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
 * @作者:Hades , 创建日期:Apr 18, 2007
 *
 * 汕头大学03计算机本科
 * 
 */

public class ChineseSegmentFrame extends JFrame
{

    /**
     * 异常信息每行所包含的字符数
     */
    private static final int ROWLEN = 100;

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 6403322054219428582L;

    /**
     * 弹出菜单
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
     * 中文分词器
     */
    private HickwallQueryAnalyzer analyer = null;

    /**
     * 目标文件文本框
     */
    private JTextField dstFileOutput;

    private JTextArea dstTextArea;

    /**
     * 姓名统计词典
     */
    private ChineseNameDictionary nameDic = null;

    /**
     * 非姓名统计词典
     */
    private NotChineseNameDictionary notNameDic = null;

    /**
     * 源文件文本框
     */
    private JTextField srcFileInput;

    private JTextArea srcTextArea;

    /**
     * 识别所得的中文姓名容器
     */
    private Map<String, String> tempName = null;

    /**
     * 显示识别所得姓名的表格
     */
    private JTable tempNameTable;

    /**
     * 数据模型
     */
    private ChineseNameTempTableModel tm = new ChineseNameTempTableModel();

    /**
     * 计时器
     */
    private final StopWatch watch = new StopWatch();

    /**
     * 配置文档路径
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
            this.setTitle("hickwall中文分词演示程序");

            // 添加主Panel
            final JPanel mainPanel = new JPanel();
            this.getContentPane().add(mainPanel, BorderLayout.CENTER);

            final JPanel xmlPanel = new JPanel();
            this.getContentPane().add(xmlPanel, BorderLayout.NORTH);
            xmlPanel
                    .setBorder(new TitledBorder(new EtchedBorder(), "XML配置文档路径"));

            final JLabel xmlPathLabel = new JLabel();
            xmlPathLabel.setText("请选择XML配置文档的路径");
            xmlPanel.add(xmlPathLabel);

            // 添加TabbedPane
            final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,
                    JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane.setPreferredSize(new Dimension(530, 370));
            mainPanel.add(tabbedPane);

            // ////////////////////////////////////////////////////////////////////
            // 添加标签1：用于直接文件分词
            // ////////////////////////////////////////////////////////////////////
            final JPanel fileSegmentPanel = new JPanel();
            fileSegmentPanel.setLayout(null);
            tabbedPane.addTab("分词操作1", fileSegmentPanel);

            srcFileInput = new JTextField();
            srcFileInput.setBounds(122, 97, 300, 21);
            fileSegmentPanel.add(srcFileInput);

            final JLabel srcFileLabel = new JLabel();
            srcFileLabel.setText("等待分词文件：");
            srcFileLabel.setBounds(31, 100, 95, 15);
            fileSegmentPanel.add(srcFileLabel);

            dstFileOutput = new JTextField();
            dstFileOutput.setBounds(122, 182, 300, 21);
            fileSegmentPanel.add(dstFileOutput);

            final JLabel dstFileLabel = new JLabel();
            dstFileLabel.setText("结果输出文件：");
            dstFileLabel.setBounds(31, 185, 95, 15);
            fileSegmentPanel.add(dstFileLabel);

            final JButton srcBrowserButton = new JButton();
            srcBrowserButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // 显示文件选择框
                    JFileChooser chooser = new JFileChooser(new File("./"));
                    // 设置过滤器
                    chooser.setFileFilter(new SpecailFileFilter(".txt"));
                    int result = chooser
                            .showOpenDialog(ChineseSegmentFrame.this);
                    // 判断是否选中文件
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            // 获取选中的文件路径
                            String path = chooser.getSelectedFile()
                                    .getCanonicalPath();
                            // 监测源文件是否与目标文件相同
                            if (dstFileOutput.getText().equals(path))
                                throw new Exception("源文件不能与目标文件相同！");
                            else
                            {
                                // 将路径放入srcInput
                                srcFileInput.setText(path);
                            }
                        }
                        catch (Exception ex)
                        {
                            // hades 警告
                            alertException(ex);
                        }
                    }
                }
            });
            srcBrowserButton.setText("浏览...");
            srcBrowserButton.setBounds(428, 90, 75, 35);
            fileSegmentPanel.add(srcBrowserButton);

            final JButton dstBrowserButton = new JButton();
            dstBrowserButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // 显示文件选择框
                    JFileChooser chooser = new JFileChooser(new File("./"));
                    // 设置过滤器
                    chooser.setFileFilter(new SpecailFileFilter(".txt"));
                    int result = chooser
                            .showOpenDialog(ChineseSegmentFrame.this);
                    // 判断是否选中文件
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            // 获取选中的文件路径
                            String path = chooser.getSelectedFile()
                                    .getCanonicalPath();
                            // 监测源文件是否与目标文件相同
                            if (srcFileInput.getText().equals(path))
                                throw new Exception("源文件不能与目标文件相同！");
                            else
                            {
                                // 将路径放入dstOutput
                                dstFileOutput.setText(path);
                            }
                        }
                        catch (Exception ex)
                        {
                            // hades 警告
                            alertException(ex);
                        }
                    }
                }
            });
            dstBrowserButton.setText("浏览...");
            dstBrowserButton.setBounds(428, 175, 75, 35);
            fileSegmentPanel.add(dstBrowserButton);

            final JButton fileSegmentButton = new JButton();
            fileSegmentButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        // 分词操作
                        if (ChineseSegmentFrame.this.analyer != null)
                        {
                            if (srcFileInput.getText() == null
                                    || StringUtils.isBlank(srcFileInput
                                            .getText()))
                                throw new Exception("等待分词文件不能为空");
                            if (dstFileOutput.getText() == null
                                    || StringUtils.isBlank(dstFileOutput
                                            .getText()))
                                throw new Exception("结果输出文件不能为空");
                            if (srcFileInput.getText().equals(
                                    dstFileOutput.getText()))
                                throw new Exception("等待分词文件不能与结果输出文件相同");
                            // 计时开始
                            watch.reset();
                            watch.start();
                            // 分词
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
                            // 计时结束
                            watch.stop();
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "分词成功。耗时："
                                            + watch.getTime() + "ms", "提示",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                            throw new Exception("请选择XML配置文档");
                    }
                    catch (Exception ex)
                    {
                        // hades 警告
                        alertException(ex);
                    }
                }
            });
            fileSegmentButton.setText("执行分词");
            fileSegmentButton.setBounds(210, 300, 100, 30);
            fileSegmentPanel.add(fileSegmentButton);

            // ////////////////////////////////////////////////////////////////////
            // 添加标签2：用于直接文本分词
            // ////////////////////////////////////////////////////////////////////
            final JPanel textSegmentPanel = new JPanel();
            textSegmentPanel.setLayout(null);
            tabbedPane.addTab("分词操作2", textSegmentPanel);

            // 分词源
            final JLabel srcTextLabel = new JLabel();
            srcTextLabel.setText("待分词内容：");
            srcTextLabel.setBounds(30, 20, 85, 15);
            textSegmentPanel.add(srcTextLabel);

            final JScrollPane srcScrollPane = new JScrollPane();
            srcScrollPane.setBounds(30, 41, 450, 110);
            srcScrollPane.setWheelScrollingEnabled(true);
            textSegmentPanel.add(srcScrollPane);

            srcTextArea = new JTextArea();
            srcTextArea.setLineWrap(true);
            srcScrollPane.setViewportView(srcTextArea);

            // 分词结果
            final JLabel dstTextLabel = new JLabel();
            dstTextLabel.setText("分词后结果：");
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
                        // 分词操作
                        if (ChineseSegmentFrame.this.analyer != null)
                        {
                            if (srcTextArea.getText() == null
                                    || StringUtils.isBlank(srcTextArea
                                            .getText()))
                                throw new Exception("待分词内容不能为空");
                            // 计时开始
                            watch.reset();
                            watch.start();
                            // 分词
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
                            // 计时结束
                            watch.stop();
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "分词成功。耗时："
                                            + watch.getTime() + "ms", "提示",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                            throw new Exception("请选择XML配置文档");
                    }
                    catch (Exception ex)
                    {
                        // hades 警告
                        alertException(ex);
                    }

                }
            });
            textSegmentButton.setText("执行分词");
            textSegmentButton.setBounds(210, 300, 100, 30);
            textSegmentPanel.add(textSegmentButton);

            // ////////////////////////////////////////////////////////////////////
            // 添加标签3：识别获得的中文姓名
            // ////////////////////////////////////////////////////////////////////
            final JPanel tempNamePanel = new JPanel();
            tempNamePanel.setLayout(null);
            tabbedPane.addTab("识别获得的中文姓名", tempNamePanel);

            final JLabel tempNameLabel = new JLabel();
            tempNameLabel.setText("识别获得的中文姓名：");
            tempNameLabel.setBounds(10, 10, 135, 15);
            tempNamePanel.add(tempNameLabel);

            final JScrollPane tempNameScollPane = new JScrollPane();
            tempNameScollPane.setBounds(10, 35, 505, 295);
            tempNamePanel.add(tempNameScollPane);

            tempNameTable = new JTable();
            // 设置不能拖动
            tempNameTable.getTableHeader().setReorderingAllowed(false);
            // 设置不能多选
            tempNameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            tempNameScollPane.setViewportView(tempNameTable);

            final JButton tempNameFreshButton = new JButton();
            tempNameFreshButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // 设置数据源
                    tm.setTempName(tempName);
                    tempNameTable.setModel(tm);
                    // 刷新数据
                    tempNameTable.updateUI();
                }
            });
            tempNameFreshButton.setText("刷新");
            tempNameFreshButton.setBounds(370, 5, 100, 25);
            tempNamePanel.add(tempNameFreshButton);

            // 弹出菜单
            final JPopupMenu tempNamePopupMenu = new JPopupMenu();
            addPopup(tempNameTable, tempNamePopupMenu);

            // 添加到姓名统计库的菜单按钮
            final JMenuItem addNameMenuItem = new JMenuItem();
            addNameMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (tempName == null || nameDic == null)
                        return;

                    // 添加到姓名统计词典
                    // 获取被选中的行号
                    int rowIndex = tempNameTable.getSelectedRow();
                    if (rowIndex >= 0)
                    {
                        String name = (String) tm.getValueAt(rowIndex, 2);
                        int result = JOptionPane.showConfirmDialog(
                                ChineseSegmentFrame.this, "确定将加入到姓名[ " + name
                                        + " ]统计库？", "确认操作",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        if (result == JOptionPane.OK_OPTION)
                        {
                            tempName.remove(name);
                            nameDic.insertWord(name);
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "添加成功", "操作成功",
                                    JOptionPane.INFORMATION_MESSAGE);
                            tempNameFreshButton.doClick();
                        }
                    }
                }
            });
            addNameMenuItem.setText("从临时表中删除并[ 添加 ]到姓名统计库");
            tempNamePopupMenu.add(addNameMenuItem);

            // 从临时姓名库中删除的菜单按钮
            final JMenuItem delNameMenuItem = new JMenuItem();
            delNameMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (tempName == null || nameDic == null)
                        return;

                    // 从临时列表中删除
                    // 获取被选中的行号
                    int rowIndex = tempNameTable.getSelectedRow();
                    if (rowIndex >= 0)
                    {
                        String name = (String) tm.getValueAt(rowIndex, 2);
                        int result = JOptionPane.showConfirmDialog(
                                ChineseSegmentFrame.this, "确定将姓名[ " + name
                                        + " ]添加到姓名黑名单？", "确认操作",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        if (result == JOptionPane.OK_OPTION)
                        {
                            tempName.remove(name);
                            notNameDic.insertWord(name);
                            JOptionPane.showMessageDialog(
                                    ChineseSegmentFrame.this, "添加成功", "操作成功",
                                    JOptionPane.INFORMATION_MESSAGE);
                            tempNameFreshButton.doClick();
                        }
                    }
                }
            });
            delNameMenuItem.setText("从临时表中删除并[ 添加 ]到姓名黑名单");
            tempNamePopupMenu.add(delNameMenuItem);

            // ////////////////////////////////////////////////////////////////////
            // 设置菜单栏
            // ////////////////////////////////////////////////////////////////////
            final JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);

            final JMenu actionMenu = new JMenu();
            actionMenu.setText("操作");
            menuBar.add(actionMenu);

            // 选择配置文档按钮
            final JMenuItem chooseXmlItem = new JMenuItem();
            chooseXmlItem.addActionListener(new ActionListener()
            {
                @SuppressWarnings("unchecked")
                public void actionPerformed(ActionEvent e)
                {
                    // 显示文件选择框
                    JFileChooser chooser = new JFileChooser(new File("./"));
                    // 设置过滤器
                    chooser.setFileFilter(new SpecailFileFilter(".xml"));
                    int result = chooser
                            .showOpenDialog(ChineseSegmentFrame.this);
                    // 判断是否选中文件
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            // 获取选中的文件路径
                            String path = chooser.getSelectedFile()
                                    .getCanonicalPath();
                            if (xmlPath == null || !xmlPath.equals(path))
                            {
                                // 设置xml路径
                                xmlPath = path;
                                xmlPathLabel.setText(path);
                                // 初始化中文分词分析器
                                ApplicationContext context = new FileSystemXmlApplicationContext(
                                        path);
                                ChineseSegmentFrame.this.analyer = (HickwallQueryAnalyzer) context
                                        .getBean("hickwallQueryAnalyzer");
                                // 获取识别所得的临时中文姓名容器
                                tempName = (Map<String, String>) context
                                        .getBean("tempNameList");
                                // 获取中文姓名统计词典
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
                            // hades 警告
                            alertException(ex);
                        }
                    }
                }
            });
            chooseXmlItem.setText("选择配置文档");
            actionMenu.add(chooseXmlItem);

            actionMenu.addSeparator();

            // 关于按钮
            final JMenuItem aboutItem = new JMenuItem();
            aboutItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(ChineseSegmentFrame.this,
                            "hickwall中文分词 [1.0]", "关于",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            aboutItem.setText("关于");
            actionMenu.add(aboutItem);

            // 退出按钮
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
            exitItem.setText("退出");
            actionMenu.add(exitItem);
        }
        catch (Exception ex)
        {
            // hades 警告
            alertException(ex);
        }
    }

    /**
     * 以提示框的模式抛出异常信息
     * 
     * @param ex
     *            异常信息
     */
    private void alertException(Exception ex)
    {
        // 控制台输出错误信息
        ex.printStackTrace();

        // 异常信息分行
        String msg = "";
        String leftStr = ex.getMessage();
        while (leftStr.length() > ROWLEN)
        {
            msg += leftStr.substring(0, ROWLEN);
            msg += "\n";
            leftStr = leftStr.substring(ROWLEN);
        }
        msg += leftStr;

        JOptionPane.showMessageDialog(ChineseSegmentFrame.this, msg, "警告",
                JOptionPane.ERROR_MESSAGE);
    }
};
