package com.example.generator.util;

import com.example.generator.pojo.domain.UserDO;
import com.example.generator.pojo.enums.AuthorityEnum;
import org.springframework.stereotype.Component;

/**
 * <p>
 * AuthTools描述:权限处理
 * <p>
 * 包名称：com.example.generator.util
 * 类名称：AuthToolsUtil
 * 全路径：com.example.generator.util.AuthToolsUtil
 * 类描述：权限处理
 *
 * @author Administrator-YHL
 * @date 2023年11月14日 13:59
 */
@Component
public class AuthToolsUtil {
    /**
     * Description:
     * 给user 添加 authoritys 的权限
     *
     * @param user:
     * @param authoritys:
     * @date 2020/12/13 10:11
     **/
    public void addUserAuth(UserDO user, AuthorityEnum... authoritys) {
        user.setAuth(this.addAuth(user.getAuth(), authoritys));
    }

    /**
     * Description:
     * 给user 删除 authoritys 的权限
     *
     * @param user:
     * @param authoritys:
     * @date 2020/12/13 10:11
     **/
    public void delUserAuth(UserDO user, AuthorityEnum... authoritys) {
        user.setAuth(this.delAuth(user.getAuth(), authoritys));
    }

    /**
     * Description:
     * user对file执行operation操作
     *
     * @param user:
     * @param operation:
     * @date 2020/12/13 10:11
     **/
    public void execOpera(UserDO user, AuthorityEnum operation) {
        // 执行操作主程序

        // 执行判断逻辑，只有user都有operation的权限的时候才可以执行
        // 比如 user:0B101 operation:0B100 就可以
        // 比如 user:0B001 operation:0B100 不可以 user用户本身就没有读权限
        // 细粒度分析：只要对应位置的二进制都是1就为true,否则为false
        if (Boolean.TRUE.equals(this.judgeOperation(user.getAuth(), operation))) {
            System.out.println(user.getUserName() + "进行" + operation.getName() + "操作成功");
        } else {
            System.out.println(user.getUserName() + "进行" + operation.getName() + "操作失败");
        }

    }

    /**
     * Description:
     * 添加 authoritys 的权限
     *
     * @date 2020/12/13 10:11
     **/
    private Integer addAuth(Integer integer, AuthorityEnum... authorities) {
        // 添加权限主程序

        // 获取本次操作的实际权限集合
        Integer mergeAuth = mergeAuth(authorities);
        // 返回二者的合并即可
        return integer | mergeAuth;
    }

    private Integer delAuth(Integer integer, AuthorityEnum... authorities) {
        // 删除权限主程序

        // 获取本次操作的实际权限集合
        Integer mergeAuth = mergeAuth(authorities);

        // 返回二者的差
        // 例子：user的权限0B110（读写权限）删除0B011(执行与写权限)，结果为0B100(读权限)
        // 细粒度分析：0+0= 0，1+0 = 1， 0+1=0，1+1=0 （+代表位运算符号可能是| 也可能是 & 也可能是组合的）
        integer = ~(~integer | mergeAuth);
        return integer;
    }

    /**
     * Description:
     * 用于计算出本次操作总权限是什么
     *
     * @param authorities: 权限集合
     * @return java.lang.Integer: 合并总权限
     * @date 2020/12/13 10:27
     **/
    private Integer mergeAuth(AuthorityEnum... authorities) {
        int auth = 0B000;
        for (AuthorityEnum authority : authorities) {
            // 二进制位与操作
            // 解析：此处是合并操作，说明是要从 0 -> 1的变化
            // 例子：0B000(空权限) + 0B100(读权限) = 0B100(读权限)
            // 位运算细粒度分析： 0+1=1、0+0=0、1+0=1，1+1=1。（+代表位运算符号可能是| 也可能是 & 也可能是组合的）
            auth = auth | authority.getCode();
        }
        return auth;
    }

    private Boolean judgeOperation(Integer auth, AuthorityEnum authority) {
        // 细粒度分析 0+0=0, 0+1=0, 1+0=0, 1+1=1
        return (auth & authority.getCode()) == authority.getCode();
    }

    /**
     * Description:
     * 打印interger所拥有的权限
     *
     * @param integer:
     * @return java.lang.String
     * @date 2020/12/13 11:20
     **/
    public String pintf(Integer integer) {
        String str = "【";
        if (this.judgeOperation(integer, AuthorityEnum.READABLE)) {
            str += AuthorityEnum.READABLE.getName();
        }
        if (this.judgeOperation(integer, AuthorityEnum.WRITABLE)) {
            str += "、" + AuthorityEnum.WRITABLE.getName();
        }
        if (this.judgeOperation(integer, AuthorityEnum.RUNNABLE)) {
            str += "、" + AuthorityEnum.RUNNABLE.getName();
        }
        return str + "】";
    }
}
