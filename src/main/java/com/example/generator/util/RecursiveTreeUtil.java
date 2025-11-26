package com.example.generator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * RecursiveTreeUtil描述:递归
 * <p>
 * 包名称：com.example.generator.util
 * 类名称：RecursiveTreeUtil
 * 全路径：com.example.generator.util.RecursiveTreeUtil
 * 类描述：递归
 *
 * @author Administrator-YHL
 * @date 2025年10月14日 11:10
 */
public class RecursiveTreeUtil {
    /**
     * 通用递归方法 - 构建树形结构
     * @param <T> 数据类型
     * @param <ID> ID字段类型
     * @param dataList 原始数据列表
     * @param parentIdFunction 获取父级标识的函数
     * @param idFunction 获取当前记录ID的函数
     * @param rootId 根节点ID值
     * @return 树形结构列表
     */
    public static <T, ID> List<T> buildTree(List<T> dataList,
                                            Function<T, ID> parentIdFunction,
                                            Function<T, ID> idFunction,
                                            ID rootId) {
        // 基础情况：数据为空时直接返回
        if (dataList == null || dataList.isEmpty()) {
            return new ArrayList<>();
        }

        // 按父ID分组，提高查找效率
        Map<ID, List<T>> groupedByParent = dataList.stream()
                .collect(Collectors.groupingBy(parentIdFunction));

        // 递归构建树
        return buildTreeRecursive(groupedByParent, idFunction, rootId);
    }

    /**
     * 递归构建子树
     * @param <T> 数据类型
     * @param <ID> ID字段类型
     * @param groupedData 按父ID分组的数据
     * @param idFunction 获取ID的函数
     * @param parentId 当前父ID
     * @return 子树节点列表
     */
    private static <T, ID> List<T> buildTreeRecursive(Map<ID, List<T>> groupedData,
                                                      Function<T, ID> idFunction,
                                                      ID parentId) {
        // 基础情况：没有子节点
        List<T> children = groupedData.get(parentId);
        if (children == null || children.isEmpty()) {
            return new ArrayList<>();
        }

        // 处理每个子节点
        List<T> result = new ArrayList<>();
        for (T item : children) {
            // 这里可以根据需要添加子节点处理逻辑
            result.add(item);
            // 递归处理孙节点
            ID currentId = idFunction.apply(item);
            List<T> grandchildren = buildTreeRecursive(groupedData, idFunction, currentId);
            // 如果需要将子节点添加到特定字段，可以在这里处理
        }

        return result;
    }

    /**
     * 通用递归遍历方法
     * @param <T> 数据类型
     * @param dataList 数据列表
     * @param parentIdFunction 获取父级标识的函数
     * @param idFunction 获取当前记录ID的函数
     * @param parentId 当前处理的父ID
     * @param processor 处理函数
     */
    public static <T, ID> void traverseRecursively(List<T> dataList,
                                                   Function<T, ID> parentIdFunction,
                                                   Function<T, ID> idFunction,
                                                   ID parentId,
                                                   Function<T, Boolean> processor) {
        // 基础情况
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        // 查找当前父ID下的所有子项
        for (T item : dataList) {
            if (Objects.equals(parentIdFunction.apply(item), parentId)) {
                // 处理当前项
                Boolean shouldContinue = processor.apply(item);
                if (shouldContinue != null && !shouldContinue) {
                    return; // 提前终止
                }

                // 递归处理子项
                ID currentId = idFunction.apply(item);
                traverseRecursively(dataList, parentIdFunction, idFunction, currentId, processor);
            }
        }
    }

    /**
     *
     * // 假设有一个部门实体类
     * class Department {
     *     private Long id;
     *     private String name;
     *     private Long parentId;
     *
     *     // 构造函数、getter、setter省略
     * }
     *
     * // 使用示例
     * List<Department> departments = getDepartments(); // 获取部门数据
     *
     * // 构建树形结构，指定 parentId 为父级标识列
     * List<Department> tree = RecursiveTreeBuilder.buildTree(
     *     departments,
     *     dept -> dept.getParentId(),  // 指定 parentId 字段为父级标识
     *     dept -> dept.getId(),        // 指定 id 字段为当前记录标识
     *     0L                           // 根节点ID为0
     * );
     *
     * // 递归遍历处理
     * RecursiveTreeBuilder.traverseRecursively(
     *     departments,
     *     dept -> dept.getParentId(),  // 父级标识列
     *     dept -> dept.getId(),        // 当前记录标识列
     *     0L,                          // 从根节点开始
     *     dept -> {
     *         System.out.println("处理部门: " + dept.getName());
     *         return true; // 继续处理
     *     }
     * );
     *
     *
     *
     */
}