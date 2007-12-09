import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


//了,有空再搞一个
 class PictureCombine extends JFrame {
	 private static final long serialVersionUID = 4471162236312833978L;
	 private final int winw, winh, maxw, maxh; // 屏幕长宽
	 private int cols, rows/**//*, tw, th, w, h*/;// 拼图的行列数与原图总长宽及每一小块图的长宽
	 private double tw, th, w, h;
	 //private int tw, th, w, h; // 画出的总长宽及一小块长宽
	 private int curcol, currow;

	 private Container c;

	 JTextField col, row;

	 JButton open, close, start, confusion, restart, look;

	 JLabel rowlabel = new JLabel ("行数"), collabel = new JLabel ("列数");

	 public Image img;

	 public static BufferedImage bufferimg;

	 public static PictureCombine picture_combine_;
 
	 File choosingPath_;

	 Canvas canvas;

	 Graphics bufferg;

	 int[][] imgdata;

	 static boolean hasstart = false, islook = false;
	 MyKeyListener keyListener_ = new MyKeyListener();
	 MyMouseListener mouseListener_ = new MyMouseListener();
	 private JDialog aboutDlg_;
  
	 public PictureCombine() {
		 super ("画图");
		 winw = Toolkit.getDefaultToolkit().getScreenSize().width;
		 winh = Toolkit.getDefaultToolkit().getScreenSize().height;
		 cols = 3;
		 rows = 3;
		 col = new JTextField ("3");
		 row = new JTextField ("3");
      col.setColumns (2);
      row.setColumns (2);
      open = new JButton ("打开新图");
      start = new JButton ("开始");
      restart = new JButton ("重新开始");
      confusion = new JButton ("打乱");
      look = new JButton ("查看原图");
      close = new JButton ("退出");
      JButton about = new JButton ("游戏说明");
      setDefaultCloseOperation (EXIT_ON_CLOSE);
      setUndecorated (true);
      c = getContentPane();
      c.setLayout (new BorderLayout());
      // setBounds (winw / 2 - 200, winh / 2 - 150, 400, 300);
      setBounds (0, 0, winw, winh);
      JPanel panel = new JPanel();
      panel.setLayout (new FlowLayout());
      panel.add (rowlabel);
      panel.add (row);
      panel.add (collabel);
      panel.add (col);
      panel.add (open);
      panel.add (start);
      panel.add (restart);
      panel.add (confusion);
      panel.add (look);
      panel.add (close);
      panel.add(about);
      close.addActionListener (new ActionListener() {
          public void actionPerformed (ActionEvent e) {
              System.exit (0);
          }
      });
      open.addActionListener (new ActionListener() {
          public void actionPerformed (ActionEvent e) {
              browse();
              canvas.requestFocus();
          }
      });
      start.addActionListener (new ActionListener() {
          public void actionPerformed (ActionEvent e) {
              startmethod();
              canvas.setEnabled(true);
              canvas.requestFocus();
          }
      });
      restart.addActionListener (new ActionListener() {
          public void actionPerformed (ActionEvent e) {
              start.setEnabled (true);
              col.setEnabled (true);
              row.setEnabled (true);
              confusion.setEnabled (false);
              restart.setEnabled (false);
              look.setEnabled (false);
              canvas.setEnabled(false);
              canvas.requestFocus();
          }
      });
      confusion.addActionListener (new ActionListener() {
          public void actionPerformed (ActionEvent e) {
              radomnum();
              drawbufferedimage();
              canvas.requestFocus();
          }
      });
      look.addActionListener (new ActionListener() {
          public void actionPerformed (ActionEvent e) {
              if (islook) {
                  confusion.setEnabled (true);
                  restart.setEnabled (true);
                  look.setText ("查看原图");
                  islook = false;
                  canvas.paint (canvas.getGraphics());
                  canvas.addKeyListener (keyListener_);
                  canvas.addMouseListener (mouseListener_);
              } else {
                  confusion.setEnabled (false);
                  restart.setEnabled (false);
                  look.setText ("回到拼图");
                  islook = true;
                  canvas.paint (canvas.getGraphics());
                  canvas.removeKeyListener(keyListener_);
                  canvas.removeMouseListener(mouseListener_);
              }
              canvas.requestFocus();
          }
      });
      about.addActionListener (new ActionListener() {
          public void actionPerformed (ActionEvent e) {
              if (aboutDlg_ == null) {
                  aboutDlg_ = new JDialog(PictureCombine.this, "游戏说明", true);
                  aboutDlg_.setSize(500, 400);
                  aboutDlg_.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                  addKeyboardActionEscapExitHide(aboutDlg_);
                  {
                      JPanel panel = new JPanel();
                      panel.setLayout(new BorderLayout());
                      {
                          String textAbout = "<html><br>游戏说明　( 程序版本:1.0 )<br></html>";
                          JLabel label = new JLabel(textAbout);
                          label.setHorizontalAlignment(JLabel.CENTER);
                          panel.add(label, BorderLayout.NORTH);
                      }
                      {
                          JTextArea area = new JTextArea(20, 30);
                          String textAbout = "　　在开始游戏前，可先设置要把图片分成几行几列(3-9)，如未设置刚默认设为３行３列。\n\n" +
                                  "　　“打开新图”　即选择一幅新的图片，以供游戏。\n\n" +
                                  "　　“开始”　　　在选择了图片后进行游戏。\n\n" +
                                  "　　“重新开始”　点击后可以重新选取图片，设置行数与列数。\n\n" +
                                  "　　“打乱”　　　把图片打乱，可以多次打乱。\n\n" +
                                  "　　“查看原图”　与“回到拼图”在拼图与原图点切换。\n\n" +
                                  "　　“退出”　　　离开并关闭游戏。\n\n\n" +
                                  "　　　　　　　　　　　　　　　　　　　　　Make by 沉思的狗 2007-07-30";
                          area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                          area.setText(textAbout);
                          area.setAutoscrolls(true);
                          area.setEditable(false);
                          area.setBackground(aboutDlg_.getBackground());
                          area.setLineWrap(true);
                          JScrollPane scrollpanel = new JScrollPane(area);
                          scrollpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                          scrollpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                          panel.add(scrollpanel, BorderLayout.CENTER);
                      }
                      aboutDlg_.getContentPane().add(panel);
                  }
              }
              aboutDlg_.setLocationRelativeTo(PictureCombine.this);
              aboutDlg_.setResizable(false);
              aboutDlg_.show();
              aboutDlg_.toFront();
          }
      });
      start.setEnabled (false);
      restart.setEnabled (false);
      confusion.setEnabled (false);
      look.setEnabled (false);
      c.add (panel, BorderLayout.SOUTH);
      canvas = new MyCanvas();
      JPanel panelc = new JPanel();
      panelc.add (canvas);
      maxw = winw;
      maxh = winh - 50;
      canvas.setSize (maxw, maxh);
      panelc.setPreferredSize (new Dimension (maxw, maxh));
      c.add (panelc, BorderLayout.CENTER);
      // System.out.println (maxw + " * " + maxh);
      // addKeyListener (new MyKeyListener());
      setVisible (true);
      addKeyListener (new KeyListener() {
          public void keyTyped (KeyEvent e) {
              canvas.requestFocus();
          }

          public void keyPressed (KeyEvent e) {
              canvas.requestFocus();
          }

          public void keyReleased (KeyEvent e) {
              canvas.requestFocus();
          }
      });
      KeyListener keyL = new KeyListener() {
          public void keyTyped (KeyEvent e) {
              if (isDirectKey(e))
                  canvas.requestFocus();
          }

          public void keyPressed (KeyEvent e) {
              if (isDirectKey(e))
                  canvas.requestFocus();
          }

          public void keyReleased (KeyEvent e) {
              if (isDirectKey(e))
                  canvas.requestFocus();
          }
      };
      KeyListener keyLUpDown = new KeyListener() {
          public void keyTyped (KeyEvent e) {
              keyUpDownForColRow(e);
          }

          public void keyPressed (KeyEvent e) {
              keyUpDownForColRow(e);
          }

          public void keyReleased (KeyEvent e) {
              //keyUpDownForColRow(e);
          }
      };
      row.addKeyListener(keyL);
      col.addKeyListener(keyL);
      row.setDocument(new TextDocument());
      col.setDocument(new TextDocument());
      row.addKeyListener(keyLUpDown);
      col.addKeyListener(keyLUpDown);
      row.setCaretColor(Color.lightGray);
      col.setCaretColor(Color.lightGray);
      row.setText("3");
      col.setText("3");
      close.addKeyListener(keyL);
      open.addKeyListener(keyL);
      start.addKeyListener(keyL);
      restart.addKeyListener(keyL);
      confusion.addKeyListener(keyL);
      look.addKeyListener(keyL);
      about.addKeyListener(keyL);
  }
  
  private void keyUpDownForColRow(KeyEvent e) {
      switch(e.getKeyCode()) {
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_RIGHT:
          JTextField field = (JTextField)e.getSource();
          int num = 3;
          try {
              num = Integer.parseInt(field.getText());
          } catch (Exception exp) {
              return;
          }
          if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT) {
              if (num > 3) {
                  field.setText(Integer.toString(--num));
              }
          } else {
              if (num < 9) {
                  field.setText(Integer.toString(++num));
              }
          }
          break;
      default:
          break;
  }
  }
  private boolean isDirectKey(KeyEvent e) {
      switch(e.getKeyCode()) {
          case KeyEvent.VK_UP:
          case KeyEvent.VK_W:
          case KeyEvent.VK_DOWN:
          case KeyEvent.VK_S:
          case KeyEvent.VK_LEFT:
          case KeyEvent.VK_A:
          case KeyEvent.VK_RIGHT:
          case KeyEvent.VK_D:
              return true;
          default:
              return false;
      }
  }

  public static PictureCombine getInstance() {
      if (picture_combine_ == null) {
          picture_combine_ = new PictureCombine();
      }
      return picture_combine_;
  }

  public static void main (String[] args) {
      // JFrame f = new picture_combine();
      JFrame f = PictureCombine.getInstance();
      f.setVisible (true);
  }

  protected void startmethod() {
      // 打乱图的顺序

      try {
          cols = Integer.parseInt (col.getText());
          rows = Integer.parseInt (row.getText());
      } catch (Exception e) {
          cols = rows = 3;
      } finally {
          if (cols > 10) {
              cols = 9;
          }else if (cols < 3) {
              cols = 3;
          }
          if (rows > 10) {
              rows = 9;
          }else if (rows < 3) {
              rows = 3;
          }
          col.setText ("" + cols);
          row.setText ("" + rows);
          double tem = (cols + 2.0) / cols;
          if ( (tw * tem) > (double) maxw) {
                int tw1 = (int) Math.round (maxw / tem);
                int th1 = (int) Math.round (th / tem);
              double tw11 = maxw / tem;
              double th11 = th / tem;
              Image imgtemp = getBufferedImage (tw, th, BufferedImage.TYPE_4BYTE_ABGR);
              drawImage (imgtemp.getGraphics(), img, 0, 0, tw11, th11, 0, 0, tw, th, null);
              img = imgtemp;
              tw = tw11;
              th = th11;
          }
          w = tw / cols;
          h = th / rows;
          imgdata = new int[rows][cols + 1];
          hasstart = true;
          canvas.getGraphics().clearRect (0, 0, maxw, maxh);
          radomnum();
          drawbufferedimage();
          col.setEnabled (false);
          row.setEnabled (false);
          start.setEnabled (false);
          restart.setEnabled (true);
          confusion.setEnabled (true);
          look.setEnabled (true);
          islook = false;
          canvas.setEnabled (true);
          currow = rows - 1;
          curcol = cols - 1;
          canvas.repaint();
          canvas.requestFocus();
      }
  }

  private void radomnum() {
      boolean canBeCombined = false;
      boolean flag = true;
      int tem;
      Random r = new Random(Calendar.getInstance().getTimeInMillis());
      imgdata[rows - 1][cols] = rows * cols - 1;
      while (!canBeCombined) {
          for (int i = 0; i < rows; i++) {
              for (int k = 0; k < cols; k++) {
                  if (i == rows - 1 && k == cols - 1) {
                      imgdata[rows - 1][cols - 1] = -1;
                      curcol = cols - 1;
                      currow = rows - 1;
                      break;
                  }
                  while (flag) {
                      tem = r.nextInt (cols * rows - 1);
                      loop1: for (int i1 = 0; i1 <= i; i1++) {
                          for (int k1 = 0; k1 < cols; k1++) {
                              if (i1 == i && k1 >= k)
                                  break loop1;
                              flag = flag && (tem != imgdata[i1][k1]);
                          }
                      }
                      if (flag) {
                          imgdata[i][k] = tem;
                          flag = false;
                      } else {
                          flag = true;
                      }
                  }
                  flag = true;
              }
          }
          int sum = 0;    //只有sum为偶数时才可以拼回原图 sum = SUM ( L (i) ) + J + K
                          //L (i)是表示从第i+1号格子到第16号格子中比第i号格子的数字要小的个数
                          //空格是在第J列第K行
          sum += curcol;
          sum += currow;
          for (int i = 0; i < rows; i++) {
              for (int k = 0; k < cols; k++) {
                  for (int now = i * cols + k; now < cols * rows - 2; now++) {
                      int nowrow = now / cols;
                      int nowcol = now % cols;
                      if (imgdata[nowrow][nowcol] < imgdata[i][k]) {
                          sum++;
                      }
                  }
              }
          }
          if (sum % 2 == 1) {
              canBeCombined = true;
          }
      }
  }

  private void drawbufferedimage() {
      // bufferimg = getBufferedImage (tw, th,
      // BufferedImage.TYPE_4BYTE_ABGR);
      bufferg = bufferimg.getGraphics();
      clearRect (bufferg, 0, 0, tw, th);
      int k1, i1;
      for (int i = 0; i < rows; i++) {
          for (int k = 0; k < cols; k++) {
              if (imgdata[i][k] == -1) {
                  fillRect (bufferg, w * k, h * i, w + 1, h + 1);
              } else {
                  i1 = imgdata[i][k] / cols;
                  k1 = imgdata[i][k] % cols;
                  drawImage (bufferg, img, w * k, h * i, w * k + w, h * i + h,
                          w * k1, h * i1, w * k1 + w, h * i1 + h, null);
              }
          }
      }
      canvas.paint (canvas.getGraphics());
  }

  protected boolean browse() {
      JFileChooser chooser = new JFileChooser (choosingPath_);

       {
          ConfigFileFilter filter = new ConfigFileFilter();
          chooser.setFileFilter (filter);
          chooser.setAcceptAllFileFilterUsed (false);
          chooser.setDialogTitle ("选择图像");
      }
      int ret = chooser.showOpenDialog (this);
      if (ret == JFileChooser.APPROVE_OPTION) {
          choosingPath_ = chooser.getCurrentDirectory();
          ImageIcon ii;
          try {
              ii = new ImageIcon (chooser.getSelectedFile().getAbsolutePath());
          } catch (Exception e) {
              return false;
          }
          Image imgtemp = ii.getImage();
          if (imgtemp.getWidth (this) <= maxw
                  && imgtemp.getHeight (this) <= maxh) {
              tw = imgtemp.getWidth (this);
              th = imgtemp.getHeight (this);
              img = getBufferedImage (tw, th, BufferedImage.TYPE_4BYTE_ABGR);
          } else {
              double bw = (imgtemp.getWidth (this) + 0.0) / (maxw + 0.0);
              double bh = (imgtemp.getHeight (this) + 0.0) / (maxh + 0.0);
              if (bw >= bh) {
                  tw = maxw;
                  th = (int) Math.round ( (float) (imgtemp.getHeight (this) + 0.0) / bw);
              } else {
                  th = maxh;
                  tw = (int) Math.round ( (float) (imgtemp.getWidth (this) + 0.0) / bh);
              }
          }
          img = getBufferedImage (tw, th, BufferedImage.TYPE_4BYTE_ABGR);
          drawImage (img.getGraphics(), imgtemp, 0, 0, tw, th, 0, 0,
                  imgtemp.getWidth (this), imgtemp.getHeight (this), null);
          bufferimg = getBufferedImage (tw, th, BufferedImage.TYPE_4BYTE_ABGR);
          bufferg = bufferimg.getGraphics();
          drawImage (bufferg, img, 0, 0, tw, th, 0, 0, tw, th, null);
          canvas.repaint();
          hasstart = false;
          start.setEnabled (true);
          restart.setEnabled (false);
          confusion.setEnabled (false);
          look.setText("查看原图");
          look.setEnabled(false);
          col.setEnabled (true);
          row.setEnabled (true);
      }
      return true;
  }
  
  private class TextDocument extends PlainDocument {
      private static final long serialVersionUID = -7485623800986543697L;
      private final char[] authchar = {'3', '4', '5', '6', '7', '8', '9'};

      public void insertString(int offs, String str, AttributeSet a)
                                           throws BadLocationException {
          if ((getLength() + str.length()) > 1) {
              return;
          }

          char[] source = str.toCharArray();
          char[] result = new char[source.length];
          int j = 0;

          for (int i = 0; i < result.length; i++) {
              if (0 <= Arrays.binarySearch(authchar, source[i])) {
                  result[j++] = source[i];
              } else {
                  
              }
          }
          super.insertString(offs, new String(result, 0, j), a);     
      }
  }

  private static class ConfigFileFilter extends FileFilter {

      public String getDescription() {
          return "图像文件——*.jpg;*.gif;*.bmp";
      }

      public boolean accept (File f) {
          if (f != null) {
              if (f.isDirectory())
                  return true;
              String filename = f.getName().toLowerCase();
              if (filename.endsWith (".jpg") || filename.endsWith (".gif")
                      || filename.endsWith (".bmp")) {
                  return true;
              }
          }
          return false;
      }
  }

  class MyCanvas extends Canvas {
      private static final long serialVersionUID = -2277597023033712620L;

      MyCanvas() {
          super();
          addKeyListener (keyListener_);
          addMouseListener (mouseListener_);
      }

      public void paint (Graphics g) {
          // g.setColor (Color.yellow);
          if (!islook) {
              drawImage (g, bufferimg, (maxw - tw) / 2, (maxh - th) / 2,
                       (maxw + tw) / 2, (maxh + th) / 2, 0, 0, tw, th, this);
              if (PictureCombine.hasstart) {
                  if (imgdata[rows - 1][cols] == -1) {
                      g.setColor (Color.WHITE);
                      fillRect (g, (maxw + tw) / 2, (maxh + th) / 2 - h, w, h);
                  } else {
                      drawImage (g, img, (maxw + tw) / 2, (maxh + th) / 2 - h,
                               (maxw + tw) / 2 + w, (maxh + th) / 2, w * cols
                                      - w, h * rows - h, w * cols, h * rows,
                              this);
                  }
              }
          } else {
              clearRect (g, (maxw + tw) / 2, (maxh + th) / 2 - h, w, h);
              drawImage (g, img, (maxw - tw) / 2, (maxh - th) / 2,
                       (maxw + tw) / 2, (maxh + th) / 2, 0, 0, tw, th, this);
          }
      }
  }
  
  class MyMouseListener extends MouseAdapter {

      public void mouseReleased (MouseEvent e) {
          Point p = e.getPoint();
          try {
              Robot robot = new Robot();
              Rectangle r = getRectangle ( (maxw - tw) / 2 + w
                      * curcol, (maxh - th) / 2 + h * currow - h, w,
                      h);
              if (r.contains (p)) {
                  robot.keyPress (KeyEvent.VK_S);
                  robot.keyRelease (KeyEvent.VK_S);
              }
              r = getRectangle ( (maxw - tw) / 2 + w * curcol,
                       (maxh - th) / 2 + h * currow + h, w, h);
              if (r.contains (p)) {
                  robot.keyPress (KeyEvent.VK_W);
                  robot.keyRelease (KeyEvent.VK_W);
              }
              r = getRectangle ( (maxw - tw) / 2 + w * curcol - w,
                       (maxh - th) / 2 + h * currow, w, h);
              if (r.contains (p)) {
                  robot.keyPress (KeyEvent.VK_D);
                  robot.keyRelease (KeyEvent.VK_D);
              }
              r = getRectangle ( (maxw - tw) / 2 + w * curcol + w,
                       (maxh - th) / 2 + h * currow, w, h);
              if (r.contains (p)) {
                  robot.keyPress (KeyEvent.VK_A);
                  robot.keyRelease (KeyEvent.VK_A);
              }
          } catch (Exception ee) {
          }
      }
  
  }

  class MyKeyListener implements KeyListener {
  public void keyTyped (KeyEvent e) {

      }

      public void keyPressed (KeyEvent e) {

      }

      public void keyReleased (KeyEvent e) {
          if (hasstart) {
              boolean flag = false;
              switch (e.getKeyCode()) {
              case KeyEvent.VK_UP:
              case KeyEvent.VK_W:
                  if (currow == rows - 1
                          || (currow == rows - 1 && curcol == cols))
                      break;
                  imgdata[currow][curcol] = imgdata[currow + 1][curcol];
                  imgdata[currow + 1][curcol] = -1;
                  currow++;
                  flag = true;
                  break;
              case KeyEvent.VK_DOWN:
              case KeyEvent.VK_S:
                  if (currow == 0 || (currow == rows - 1 && curcol == cols))
                      break;
                  imgdata[currow][curcol] = imgdata[currow - 1][curcol];
                  imgdata[currow - 1][curcol] = -1;
                  currow--;
                  flag = true;
                  break;
              case KeyEvent.VK_LEFT:
              case KeyEvent.VK_A:
                  if (currow == rows - 1 && curcol == cols - 1) {
                      imgdata[currow][curcol] = imgdata[currow][curcol + 1];
                      imgdata[currow][curcol + 1] = -1;
                      curcol++;
                      boolean ok = true;
                      for (int i = 0; i < rows; i++) {
                          for (int k = 0; k < rows; k++) {
                              ok = ok && (imgdata[i][k] == i * cols + k);
                          }
                      }
                      drawbufferedimage();
                      if (ok) {
                          start.setEnabled (true);
                          col.setEnabled (true);
                          row.setEnabled (true);
                          canvas.setEnabled (false);
                          restart.setEnabled (false);
                          confusion.setEnabled (false);
                          look.setEnabled (false);
                          JOptionPane.showMessageDialog (null, "恭喜您，您完成了这幅图！",
                                  "完成", JOptionPane.INFORMATION_MESSAGE);
                      }
                      break;
                  }
                  if (curcol >= cols - 1)
                      break;
                  imgdata[currow][curcol] = imgdata[currow][curcol + 1];
                  imgdata[currow][curcol + 1] = -1;
                  curcol++;
                  flag = true;
                  break;
              case KeyEvent.VK_RIGHT:
              case KeyEvent.VK_D:
                  if (currow == rows - 1 && curcol == cols) {
                      imgdata[currow][curcol] = imgdata[currow][curcol - 1];
                      imgdata[currow][curcol - 1] = -1;
                      curcol--;
                      drawbufferedimage();
                      break;
                  }
                  if (curcol == 0)
                      break;
                  imgdata[currow][curcol] = imgdata[currow][curcol - 1];
                  imgdata[currow][curcol - 1] = -1;
                  curcol--;
                  flag = true;
                  break;
              case KeyEvent.VK_TAB:
                  canvas.requestFocus();
                  break;
              default:
                  canvas.requestFocus();
                  break;
              }
              if (flag) {
                  drawbufferedimage();
              }
          }
      }
  }
  
  private void fillRect(Graphics g, double x, double y, double width, double height) {
      fillRect(g, (int)x, (int)y, (int)width, (int)height);
  }
  private void fillRect(Graphics g, int x, int y, int width, int height) {
      g.fillRect(x, y, width, height);
  }
  
  private void clearRect(Graphics g, double x, double y, double width, double height) {
      clearRect(g, (int)x, (int)y, (int)width, (int)height);
  }
  private void clearRect(Graphics g, int x, int y, int width, int height) {
      g.clearRect(x, y, width, height);
  }
  
  private void drawImage(Graphics g, Image img, double dx1, double dy1, double dx2, double dy2, 
          double sx1, double sy1, double sx2, double sy2, ImageObserver observer) {
      drawImage(g, img, (int)dx1, (int)dy1, (int)dx2, (int)dy2, (int)sx1, (int)sy1, (int)sx2, (int)sy2, observer);
  }
  private void drawImage(Graphics g, Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
      g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
  }
  
  private BufferedImage getBufferedImage(double width, double height, int imageType) {
      return getBufferedImage((int)width, (int)height, imageType);
  }
  private BufferedImage getBufferedImage(int width, int height, int imageType) {
      return new BufferedImage (width, height, imageType);
  }
  
  private Rectangle getRectangle(double x, double y, double width, double height) {
      return getRectangle((int)x, (int)y, (int)width, (int)height);
  }
  private Rectangle getRectangle(int x, int y, int width, int height) {
      return new Rectangle(x, y, width, height);
  }
  /** *//**
   * <p>After call this function, when you putdown ESCAPE button, the Window will be hide.</p>
   * @param compnemt RootPaneContainer (now can be JApplet, JDialog, JFrame, JInternalFrame, JWindow)
   * @return true if success, else return false.
   */
  public boolean addKeyboardActionEscapExitHide(RootPaneContainer compnemt) {
      KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
      Action action = new AbstractAction(){
          private static final long serialVersionUID = -1527956543338809497L;
          public void actionPerformed(ActionEvent e) {
              Object obj = e.getSource();
              if (obj instanceof Component) {
                  Component comp = (Component)obj;
                  while (comp != null) {
                      if (comp instanceof Window || comp instanceof JInternalFrame || comp instanceof JApplet) {
                          comp.setVisible(false);
                          break;
                      }
                      comp = comp.getParent();
                  }
              }
          }
      };
      return addKeyboardAction(compnemt, keyStroke, "DogActionMapKey_EscapExitHide", action, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }
  
  /** *//**
   * <p>快捷键.</p>
   * @param condition one of (JComponent.WHEN_FOCUSED | JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT | JComponent.WHEN_IN_FOCUSED_WINDOW)
   * @return true if success, else return false.
   */
  public boolean addKeyboardAction(RootPaneContainer compnemt, KeyStroke keyStroke, String actionMapKey, Action action, int condition) {
      if (compnemt == null || keyStroke == null || actionMapKey == null || action == null) {
          return false;
      }
      JRootPane rootPane = compnemt.getRootPane();
      if (rootPane != null) {
          try {
              rootPane.getInputMap(condition).put(keyStroke, actionMapKey);
              rootPane.getActionMap().put(actionMapKey, action);
              return true;
          } catch (Exception e) {
              return false;
          }
      }
      return false;
  }
}

