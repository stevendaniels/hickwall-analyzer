/* 
 * Copyright hickwall 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
package com.novse.segmentation.view;

import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

/**
 * @author Mac Kwan 用于存储中文姓名识别后获得临时姓名列表
 */
public class ChineseNameTempTableModel extends AbstractTableModel
{
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -4455147225831087567L;

    /**
     * 识别所得的姓名临时列表
     */
    private Map<String, String> tempName = null;

    /**
     * 默认构造函数
     */
    public ChineseNameTempTableModel()
    {

    }

    /**
     * @param tempName
     *            识别所得的姓名临时列表
     */
    public ChineseNameTempTableModel(Map<String, String> tempName)
    {
        this.tempName = tempName;
    }

    /**
     * 返回列数
     * 
     * @see javax.swing.table.TableModel#ColumnCount()
     */
    public int getColumnCount()
    {
        return 3;
    }

    /**
     * 返回列名
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column)
    {
        switch (column)
        {
            case 0:
                return "行号";
            case 1:
                return "候选字符串";
            case 2:
                return "系统识别所得的中文姓名";
            default:
                return null;
        }
    }

    /**
     * 返回行数（已识别的人名个数）
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount()
    {
        if (tempName == null)
            return 0;
        else
            return tempName.size();
    }

    /**
     * @return 返回 tempName。
     */
    public Map<String, String> getTempName()
    {
        return tempName;
    }

    /**
     * 返回结果
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (tempName == null)
            return null;

        // 获取key集合
        Set<String> keySet = this.tempName.keySet();
        // 获取key
        String key = (String) keySet.toArray()[rowIndex];
        // 第一列返回行号
        if (columnIndex == 0)
            return rowIndex + 1;
        // 第二列识别所得的中文姓名的候选字符串
        else if (columnIndex == 1)
            return this.tempName.get(key);
        // 第三列返回系统识别的中文姓名
        else if (columnIndex == 2)
            return key;
        else
            return null;
    }

    /**
     * 表格是否允许编辑
     * 
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    /**
     * @param tempName
     *            要设置的 tempName。
     */
    public void setTempName(Map<String, String> tempName)
    {
        this.tempName = tempName;
    }

}
