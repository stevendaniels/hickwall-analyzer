package com.novse.segmentation.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.name.SimpleChineseNameAnalyzer;
import com.novse.segmentation.lucene.analysis.query.HickwallQueryAnalyzer;
import com.novse.segmentation.util.DictionaryUtil;

/*
 * @����:Hades , ��������:Apr 18, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */

public class ChineseSegmentFrame extends JFrame
{

    /**
     * �쳣��Ϣÿ�����������ַ���
     */
    private static final int ROWLEN = 100;

    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 6403322054219428582L;

    /**
     * �����˵�
     * 
     * @param component
     * @param popup
     */
    private static void addPopup(Component component, final JPopupMenu popup)
    {
        component.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                    showMenu(e);
            }

            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                    showMenu(e);
            }

            private void showMenu(MouseEvent e)
            {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    /**
     * Launch the application
     * 
     * @param args
     */
    public static void main(String args[])
    {
        try
        {
            ChineseSegmentFrame frame = new ChineseSegmentFrame();
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ���ķִ���
     */
    private HickwallQueryAnalyzer analyer = null;

    /**
     * Ŀ���ļ��ı���
     */
    private JTextField dstFileOutput;

    private JTextArea dstTextArea;

    /**
     * ����ͳ�ƴʵ�
     */
    private ChineseNameDictionary nameDic = null;

    /**
     * ������ͳ�ƴʵ�
     */
    private NotChineseNameDictionary notNameDic = null;

    /**
     * Դ�ļ��ı���
     */
    private JTextField srcFileInput;

    private JTextArea srcTextArea;

    /**
     * ʶ�����õ�������������
     */
    private Map<String, String> tempName = null;

    /**
     * ��ʾʶ�����������ı��
     */
    private JTable tempNameTable;

    /**
     * ����ģ��
     */
    private ChineseNameTempTableModel tm = new ChineseNameTempTableModel();

    /**
     * ��ʱ��
     */
    private final StopWatch watch = new StopWatch();

    /**
     * �����ĵ�·��
     */
    private String xmlPath = null;

    /**
     * Create the frame
     */
    public ChineseSegmentFrame()
    {
        super();
        try
        {
            this.setBounds(250, 100, 550, 495);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.setResizable(false);
            this.setTitle("hickwall���ķִ���ʾ����");

            // �����Panel
            final JPanel mainPanel = new JPanel();
            this.getContentPane().add(mainPanel, BorderLayout.CENTER);

            final JPanel xmlPanel = new JPanel();
            this.getContentPane().add(xmlPanel, BorderLayout.NORTH);
            xmlPanel
                    .setBorder(new TitledBorder(new EtchedBorder(), "XML�����ĵ�·��"));

            final JLabel xmlPathLabel = new JLabel();
            xmlPathLabel.setText("��ѡ��XML�����ĵ���·��");
            xmlPanel.add(xmlPathLabel);

            // ���TabbedPane
            final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,
                    JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane.setPreferredSize(new Dimension(530, 370));
            mainPanel.add(tabbedPane);

            // ////////////////////////////////////////////////////////////////////
            // ��ӱ�ǩ1������ֱ���ļ��ִ�
            // ////////////////////////////////////////////////////////////////////
            final JPanel fileSegmentPanel = new JPanel();
            fileSegmentPanel.setLayout(null);
            tabbedPane.addTab("�ִʲ���1", fileSegmentPanel);

            srcFileInput = new JTextField();
            srcFileInput.setBounds(122, 97, 300, 21);
            fileSegmentPanel.add(srcFileInput);

            final JLabel srcFileLabel = new JLabel();
            srcFileLabel.setText("�ȴ��ִ��ļ���");
            srcFileLabel.setBounds(31, 100, 95, 15);
            fileSegmentPanel.add(srcFileLabel);

            dstFileOutput = new JTextField();
            dstFileOutput.setBounds(122, 182, 300, 21);
            fileSegmentPanel.add(dstFileOutput);

            final JLabel dstFileLabel = new JLabel();
            dstFileLabel.setText("�������ļ���");
            dstFileLabel.setBounds(31, 185, 95, 15);
            fileSegmentPanel.add(dstFileLabel);

            final JButton srcBrowserButton = new JButton();
            srcBrowserButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // ��ʾ�ļ�ѡ���
                    JFileChooser chooser = new JFileChooser(new File("./"));
                    // ���ù�����
                    chooser.setFileFilter(new SpecailFileFilter(".txt"));
                    int result = chooser
                            .showOpenDialog(ChineseSegmentFrame.this);
                    // �ж��Ƿ�ѡ���ļ�
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            // ��ȡѡ�е��ļ�·��
                            String path = chooser.getSelectedFile()
                                    .getCanonicalPath();
                            // ���Դ�ļ��Ƿ���Ŀ���ļ���ͬ
                            if (dstFileOutput.getText().equals(path))
                                throw new Exception("Դ�ļ�������Ŀ���ļ���ͬ��");
                            else
                            {
                                // ��·������srcInput
                                srcFileInput.setText(path);
                            }
                        }
                        catch (Exception ex)
                        {
                            // hades ����
                            alertException(ex);
                        }
                    }
                }
            });
            srcBrowserButton.setText("���...");
            srcBrowserButton.setBounds(428, 90, 75, 35);
            fileSegmentPanel.add(srcBrowserButton);

            final JButton dstBrowserButton = new JButton();
            dstBrowserButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // ��ʾ�ļ�ѡ���
                    JFileChooser chooser = new JFileChooser(new File("./"));
                    // ���ù�����
                    chooser.setFileFilter(new SpecailFileFilter(".txt"));
                    int result = chooser
                            .showOpenDialog(ChineseSegmentFrame.this);
                    // �ж��Ƿ�ѡ���ļ�
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            // ��ȡѡ�е��ļ�·��
                            String path = chooser.getSelectedFile()
                                    .getCanonicalPath();
                            // ���Դ�ļ��Ƿ���Ŀ���ļ���ͬ
                            if (srcFileInput.getText().equals(path))
                                throw new Exception("Դ�ļ�������Ŀ���ļ���ͬ��");
                            else
                            {
                                // ��·������dstOutput
                                dstFileOutput.setText(path);
                            }
                        }
                        catch (Exception ex)
                        {
                            // hades ����
                            alertException(ex);
                        }
                    }
                }
            });
            dstBrowserButton.setText("���...");
            dstBrowserButton.setBounds(428, 175, 75, 35);
            fileSegmentPanel.add(dstBrowserButton);

            final JButton fileSegmentButton = new JButton();
            fileSegmentButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        // �ִʲ���
                        if (ChineseSegmentFrame.this.analyer != null)
                        {
                            if (srcFileInput.getText() == null
                                    || StringUtils.isBlank(srcFileInput
                                            .getText()))
                                throw new Exception("�ȴ��ִ��ļ�����Ϊ��");
                            if (dstFileOutput.getText() == null
                                    || StringUtils.isBlank(dstFileOutput
                                            .getText()))
                                throw new Exception("�������ļ�����Ϊ��");
                            if (srcFileInput.getText().equals(
                                    dstFileOutput.getText()))
                                throw new Exception("�ȴ��ִ��ļ�������������ļ���ͬ");
                            // ��ʱ��ʼ
                            watch.reset();
                            watch.start();
                            // �ִ�
                            FileReader in = new FileReader(srcFileInput
                                    .getText());
                            PrintWriter out = new PrintWriter(
                                    new BufferedWriter(new FileWriter(
                                            dstFileOutput.getText())));
                            TokenStream ts = analyer.tokenStream(null, in);
                            Token t;
                            while ((t = ts.next()) != null)
                            {
                                out.println(t);
                            }
                            out.flush();
                            out.close();
                            in.close();
                            // ��ʱ����
                            watch.stop();
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "�ִʳɹ�����ʱ��"
                                            + watch.getTime() + "ms", "��ʾ",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                            throw new Exception("��ѡ��XML�����ĵ�");
                    }
                    catch (Exception ex)
                    {
                        // hades ����
                        alertException(ex);
                    }
                }
            });
            fileSegmentButton.setText("ִ�зִ�");
            fileSegmentButton.setBounds(210, 300, 100, 30);
            fileSegmentPanel.add(fileSegmentButton);

            // ////////////////////////////////////////////////////////////////////
            // ��ӱ�ǩ2������ֱ���ı��ִ�
            // ////////////////////////////////////////////////////////////////////
            final JPanel textSegmentPanel = new JPanel();
            textSegmentPanel.setLayout(null);
            tabbedPane.addTab("�ִʲ���2", textSegmentPanel);

            // �ִ�Դ
            final JLabel srcTextLabel = new JLabel();
            srcTextLabel.setText("���ִ����ݣ�");
            srcTextLabel.setBounds(30, 20, 85, 15);
            textSegmentPanel.add(srcTextLabel);

            final JScrollPane srcScrollPane = new JScrollPane();
            srcScrollPane.setBounds(30, 41, 450, 110);
            srcScrollPane.setWheelScrollingEnabled(true);
            textSegmentPanel.add(srcScrollPane);

            srcTextArea = new JTextArea();
            srcTextArea.setLineWrap(true);
            srcScrollPane.setViewportView(srcTextArea);

            // �ִʽ��
            final JLabel dstTextLabel = new JLabel();
            dstTextLabel.setText("�ִʺ�����");
            dstTextLabel.setBounds(30, 160, 85, 15);
            textSegmentPanel.add(dstTextLabel);

            final JScrollPane dstScrollPane = new JScrollPane();
            dstScrollPane.setBounds(30, 181, 450, 110);
            textSegmentPanel.add(dstScrollPane);

            dstTextArea = new JTextArea();
            dstTextArea.setLineWrap(true);
            dstScrollPane.setViewportView(dstTextArea);

            final JButton textSegmentButton = new JButton();
            textSegmentButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        // �ִʲ���
                        if (ChineseSegmentFrame.this.analyer != null)
                        {
                            if (srcTextArea.getText() == null
                                    || StringUtils.isBlank(srcTextArea
                                            .getText()))
                                throw new Exception("���ִ����ݲ���Ϊ��");
                            // ��ʱ��ʼ
                            watch.reset();
                            watch.start();
                            // �ִ�
                            StringReader in = new StringReader(srcTextArea
                                    .getText());
                            StringBuffer buffer = new StringBuffer();
                            TokenStream ts = analyer.tokenStream(null, in);
                            Token t;
                            while ((t = ts.next()) != null)
                            {
                                buffer.append(t);
                                buffer.append("\n");
                            }
                            in.close();
                            dstTextArea.setText(buffer.toString());
                            // ��ʱ����
                            watch.stop();
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "�ִʳɹ�����ʱ��"
                                            + watch.getTime() + "ms", "��ʾ",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                            throw new Exception("��ѡ��XML�����ĵ�");
                    }
                    catch (Exception ex)
                    {
                        // hades ����
                        alertException(ex);
                    }

                }
            });
            textSegmentButton.setText("ִ�зִ�");
            textSegmentButton.setBounds(210, 300, 100, 30);
            textSegmentPanel.add(textSegmentButton);

            // ////////////////////////////////////////////////////////////////////
            // ��ӱ�ǩ3��ʶ���õ���������
            // ////////////////////////////////////////////////////////////////////
            final JPanel tempNamePanel = new JPanel();
            tempNamePanel.setLayout(null);
            tabbedPane.addTab("ʶ���õ���������", tempNamePanel);

            final JLabel tempNameLabel = new JLabel();
            tempNameLabel.setText("ʶ���õ�����������");
            tempNameLabel.setBounds(10, 10, 135, 15);
            tempNamePanel.add(tempNameLabel);

            final JScrollPane tempNameScollPane = new JScrollPane();
            tempNameScollPane.setBounds(10, 35, 505, 295);
            tempNamePanel.add(tempNameScollPane);

            tempNameTable = new JTable();
            // ���ò����϶�
            tempNameTable.getTableHeader().setReorderingAllowed(false);
            // ���ò��ܶ�ѡ
            tempNameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            tempNameScollPane.setViewportView(tempNameTable);

            final JButton tempNameFreshButton = new JButton();
            tempNameFreshButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // ��������Դ
                    tm.setTempName(tempName);
                    tempNameTable.setModel(tm);
                    // ˢ������
                    tempNameTable.updateUI();
                }
            });
            tempNameFreshButton.setText("ˢ��");
            tempNameFreshButton.setBounds(370, 5, 100, 25);
            tempNamePanel.add(tempNameFreshButton);

            // �����˵�
            final JPopupMenu tempNamePopupMenu = new JPopupMenu();
            addPopup(tempNameTable, tempNamePopupMenu);

            // ��ӵ�����ͳ�ƿ�Ĳ˵���ť
            final JMenuItem addNameMenuItem = new JMenuItem();
            addNameMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (tempName == null || nameDic == null)
                        return;

                    // ��ӵ�����ͳ�ƴʵ�
                    // ��ȡ��ѡ�е��к�
                    int rowIndex = tempNameTable.getSelectedRow();
                    if (rowIndex >= 0)
                    {
                        String name = (String) tm.getValueAt(rowIndex, 2);
                        int result = JOptionPane.showConfirmDialog(
                                ChineseSegmentFrame.this, "ȷ�������뵽����[ " + name
                                        + " ]ͳ�ƿ⣿", "ȷ�ϲ���",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        if (result == JOptionPane.OK_OPTION)
                        {
                            tempName.remove(name);
                            nameDic.insertWord(name);
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "��ӳɹ�", "�����ɹ�",
                                    JOptionPane.INFORMATION_MESSAGE);
                            tempNameFreshButton.doClick();
                        }
                    }
                }
            });
            addNameMenuItem.setText("����ʱ����ɾ����[ ��� ]������ͳ�ƿ�");
            tempNamePopupMenu.add(addNameMenuItem);

            // ����ʱ��������ɾ���Ĳ˵���ť
            final JMenuItem delNameMenuItem = new JMenuItem();
            delNameMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (tempName == null || nameDic == null)
                        return;

                    // ����ʱ�б���ɾ��
                    // ��ȡ��ѡ�е��к�
                    int rowIndex = tempNameTable.getSelectedRow();
                    if (rowIndex >= 0)
                    {
                        String name = (String) tm.getValueAt(rowIndex, 2);
                        int result = JOptionPane.showConfirmDialog(
                                ChineseSegmentFrame.this, "ȷ��������[ " + name
                                        + " ]��ӵ�������������", "ȷ�ϲ���",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        if (result == JOptionPane.OK_OPTION)
                        {
                            tempName.remove(name);
                            notNameDic.insertWord(name);
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "��ӳɹ�", "�����ɹ�",
                                    JOptionPane.INFORMATION_MESSAGE);
                            tempNameFreshButton.doClick();
                        }
                    }
                }
            });
            delNameMenuItem.setText("����ʱ����ɾ����[ ��� ]������������");
            tempNamePopupMenu.add(delNameMenuItem);

            // ////////////////////////////////////////////////////////////////////
            // ���ò˵���
            // ////////////////////////////////////////////////////////////////////
            final JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);

            final JMenu actionMenu = new JMenu();
            actionMenu.setText("����");
            menuBar.add(actionMenu);

            // ѡ�������ĵ���ť
            final JMenuItem chooseXmlItem = new JMenuItem();
            chooseXmlItem.addActionListener(new ActionListener()
            {
                @SuppressWarnings("unchecked")
                public void actionPerformed(ActionEvent e)
                {
                    // ��ʾ�ļ�ѡ���
                    JFileChooser chooser = new JFileChooser(new File("./"));
                    // ���ù�����
                    chooser.setFileFilter(new SpecailFileFilter(".xml"));
                    int result = chooser
                            .showOpenDialog(ChineseSegmentFrame.this);
                    // �ж��Ƿ�ѡ���ļ�
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            // ��ȡѡ�е��ļ�·��
                            String path = chooser.getSelectedFile()
                                    .getCanonicalPath();
                            if (xmlPath == null || !xmlPath.equals(path))
                            {
                                // ����xml·��
                                xmlPath = path;
                                xmlPathLabel.setText(path);
                                // ��ʼ�����ķִʷ�����
                                ApplicationContext context = new FileSystemXmlApplicationContext(
                                        path);
                                ChineseSegmentFrame.this.analyer = (HickwallQueryAnalyzer) context
                                        .getBean("hickwallQueryAnalyzer");
                                // ��ȡʶ�����õ���ʱ������������
                                tempName = (Map<String, String>) context
                                        .getBean("tempNameList");
                                // ��ȡ��������ͳ�ƴʵ�
                                nameDic = ((SimpleChineseNameAnalyzer) context
                                        .getBean("simpleChineseNameAnalyzer"))
                                        .getNameDic();
                                notNameDic = ((SimpleChineseNameAnalyzer) context
                                        .getBean("simpleChineseNameAnalyzer"))
                                        .getNotNameDic();
                            }
                        }
                        catch (Exception ex)
                        {
                            // hades ����
                            alertException(ex);
                        }
                    }
                }
            });
            chooseXmlItem.setText("ѡ�������ĵ�");
            actionMenu.add(chooseXmlItem);

            actionMenu.addSeparator();

            // ���ڰ�ť
            final JMenuItem aboutItem = new JMenuItem();
            aboutItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(ChineseSegmentFrame.this,
                            "hickwall���ķִ� [1.0]", "����",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            aboutItem.setText("����");
            actionMenu.add(aboutItem);

            // �˳���ť
            final JMenuItem exitItem = new JMenuItem();
            exitItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (nameDic != null)
                        new DictionaryUtil<ChineseNameDictionary>()
                                .writeDictionary(nameDic, "Dic/Name.stu");
                    if (notNameDic != null)
                        new DictionaryUtil<NotChineseNameDictionary>()
                                .writeDictionary(notNameDic, "Dic/UnName.stu");
                    System.exit(0);
                }
            });
            exitItem.setText("�˳�");
            actionMenu.add(exitItem);
        }
        catch (Exception ex)
        {
            // hades ����
            alertException(ex);
        }
    }

    /**
     * ����ʾ���ģʽ�׳��쳣��Ϣ
     * 
     * @param ex
     *            �쳣��Ϣ
     */
    private void alertException(Exception ex)
    {
        // ����̨���������Ϣ
        ex.printStackTrace();

        // �쳣��Ϣ����
        String msg = "";
        String leftStr = ex.getMessage();
        while (leftStr.length() > ROWLEN)
        {
            msg += leftStr.substring(0, ROWLEN);
            msg += "\n";
            leftStr = leftStr.substring(ROWLEN);
        }
        msg += leftStr;

        JOptionPane.showMessageDialog(ChineseSegmentFrame.this, msg, "����",
                JOptionPane.ERROR_MESSAGE);
    }
};
