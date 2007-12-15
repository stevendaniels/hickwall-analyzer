/*
 * @����:Hades , ��������:May 20, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.view;

import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

/**
 * @author Mac Kwan ���ڴ洢��������ʶ�������ʱ�����б�
 */
public class ChineseNameTempTableModel extends AbstractTableModel
{
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -4455147225831087567L;

    /**
     * ʶ�����õ�������ʱ�б�
     */
    private Map<String, String> tempName = null;

    /**
     * Ĭ�Ϲ��캯��
     */
    public ChineseNameTempTableModel()
    {

    }

    /**
     * @param tempName
     *            ʶ�����õ�������ʱ�б�
     */
    public ChineseNameTempTableModel(Map<String, String> tempName)
    {
        this.tempName = tempName;
    }

    /**
     * ��������
     * 
     * @see javax.swing.table.TableModel#ColumnCount()
     */
    public int getColumnCount()
    {
        return 3;
    }

    /**
     * ��������
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column)
    {
        switch (column)
        {
            case 0:
                return "�к�";
            case 1:
                return "��ѡ�ַ���";
            case 2:
                return "ϵͳʶ�����õ���������";
            default:
                return null;
        }
    }

    /**
     * ������������ʶ�������������
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
     * @return ���� tempName��
     */
    public Map<String, String> getTempName()
    {
        return tempName;
    }

    /**
     * ���ؽ��
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (tempName == null)
            return null;

        // ��ȡkey����
        Set<String> keySet = this.tempName.keySet();
        // ��ȡkey
        String key = (String) keySet.toArray()[rowIndex];
        // ��һ�з����к�
        if (columnIndex == 0)
            return rowIndex + 1;
        // �ڶ���ʶ�����õ����������ĺ�ѡ�ַ���
        else if (columnIndex == 1)
            return this.tempName.get(key);
        // �����з���ϵͳʶ�����������
        else if (columnIndex == 2)
            return key;
        else
            return null;
    }

    /**
     * ����Ƿ�����༭
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
     *            Ҫ���õ� tempName��
     */
    public void setTempName(Map<String, String> tempName)
    {
        this.tempName = tempName;
    }

}
