package cn.hniu.ui;

import cn.hniu.action.NoteAction;
import cn.hniu.entity.Note;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class HomeJF extends JFrame {
    /**
     * 控制器
     */
    NoteAction action = new NoteAction();
    //线程池


    private JPanel home = null;//笔记界面
    private JTextField search = null;//搜索框
    private JLabel title = new JLabel("笔记");//标题
    private CardLayout cardL = new CardLayout();//页面管理器
    private JPanel ui = new JPanel(cardL);//页面容器
    private JLayeredPane Home = new JLayeredPane();//主界面

    private JPanel detail = new JPanel();//详细界面
    private JButton reBtn = new JButton("<");
    private JTextArea deTitle = null;
    private JTextArea deConcent = null;


    //添加按钮
    JButton addBtn = new JButton("+") {
        {
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
            super.paintComponent(g);
            g2.dispose();
        }
    };
    //导出导入按钮
    JButton outputBtn = new JButton("导出");
    JButton inputBtn = new JButton("导入");



    public HomeJF() {
        iniJF();

        iniTop();

        iniHome();

        initAdd();

        iniData();



        setVisible(true);
    }
    //初始化详细界面
    private void initDetail(Note note) {

        detail.removeAll();
        boolean isNew;
        if(note==null){
            note = new Note("标题","内容");
            isNew=true;
        } else {
            isNew = false;
        }

        detail.setLayout(null);
        detail.setBackground(Color.white);

        JPanel top = new JPanel();
        top.setSize(800,80);
        top.setLayout(null);
        top.setBackground(Color.white);

        reBtn.setFont(new Font("宋体",Font.BOLD,32));
        reBtn.setBounds(10,20,50,50);
        reBtn.setBackground(Color.white);
        reBtn.setBorder(null);
        reBtn.setFocusable(false);
        reBtn.addActionListener(e->{
            cardL.show(ui,"Home");
            iniData();
        });
        // 删掉类字段: private JButton isBtn = new JButton("保存");
// initDetail 里改成：
        JButton isBtn = new JButton("保存");

        isBtn.setFont(new Font("宋体",Font.BOLD,25));
        isBtn.setBounds(700,10,60,60);
        isBtn.setBackground(Color.white);
        isBtn.setBorder(null);
        isBtn.setFocusable(false);
        Note finalNote = note;
        isBtn.addActionListener(e->{
            addNote(finalNote);
        });

        top.add(reBtn);
        top.add(isBtn);
        detail.add(top);

        JPanel body = new JPanel();
        body.setLayout(null);
        body.setBounds(0,80,800,1000);
        body.setBackground(Color.white);


        deTitle = new JTextArea(note.getTitle());
        deConcent = new JTextArea(note.getContent());

        deTitle.setFont(new Font("宋体",Font.BOLD,45));
        deTitle.setBounds(10,10,780,50);
        deTitle.setBackground(Color.white);
        deTitle.setBorder(null);
        deTitle.setLineWrap(true);
        deTitle.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(deTitle.getText().equals("标题")){
                    deTitle.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(deTitle.getText().isEmpty()){
                    deTitle.setText("标题");
                }
            }
        });
        body.add(deTitle);

        deConcent.setFont(new Font("宋体",Font.PLAIN,32));
        deConcent.setBounds(10,70,780,1000);
        deConcent.setBackground(Color.white);
        deConcent.setBorder(null);
        deConcent.setLineWrap(true);
        deConcent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(deConcent.getText().equals("内容")){
                    deConcent.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(deConcent.getText().isEmpty()){
                    deConcent.setText("内容");
                }
            }
        });
        body.add(deConcent);

        detail.add(body);
        detail.revalidate();
        detail.repaint();
    }
    //初始化添加按钮
    private void initAdd() {
        addBtn.setBounds(620, 1000, 78, 78);
        addBtn.setFont(new Font("宋体", Font.BOLD, 80));
        addBtn.setForeground(Color.white);
        addBtn.setBackground(Color.orange);
        addBtn.setBorder(null);
        addBtn.setFocusable(false);
        addBtn.addActionListener(e->addClick());
        Home.add(addBtn,JLayeredPane.PALETTE_LAYER);
    }
    //初始化主界面
    private void iniHome() {
        home = new JPanel();
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setBorder(null);

        JScrollPane homeJS = new JScrollPane(home);
        homeJS.setBorder(null);
        homeJS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        homeJS.setBounds(20, 240, 760, 1140);
        homeJS.getVerticalScrollBar().setUnitIncrement(20);
        Home.add(homeJS,JLayeredPane.DEFAULT_LAYER);
    }

    //初始化顶部
    private void iniTop() {
        JPanel topJP = new JPanel();
        topJP.setBounds(0, 0, 800, 230);
        topJP.setBackground(Color.white);
        topJP.setLayout(null);

        inputBtn.setBorder(null);
        inputBtn.setFocusable(false);
        inputBtn.setBackground(Color.white);
        inputBtn.setFont(new Font("宋体",Font.PLAIN,30));
        inputBtn.addActionListener(e-> {
            try {
                input();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ExecutionException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        inputBtn.setBounds(520,10,100,40);
        topJP.add(inputBtn);

        outputBtn.setBorder(null);
        outputBtn.setFocusable(false);
        outputBtn.setBackground(Color.white);
        outputBtn.setFont(new Font("宋体",Font.PLAIN,30));
        outputBtn.addActionListener(e-> {
            try {
                output();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        outputBtn.setBounds(650,10,100,40);
        topJP.add(outputBtn);


        title.setFont(new Font("宋体", Font.PLAIN, 50));
        title.setBounds(25, 80, 200, 60);
        topJP.add(title);

        search = new JTextField("搜索笔记");

        search.setFont(new Font("宋体", Font.PLAIN, 32));
        search.setBounds(27, 160, 720, 50);
        search.setForeground(Color.gray);
        search.setBackground(new Color(200, 200, 200));
        search.setBorder(null);
        // 替换原来的 search.addKeyListener(...)
        //搜索事件
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchNote();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchNote();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchNote();
            }
        });
        //聚焦事件
        search.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                clearSearch();
            }
            @Override
            public void focusLost(FocusEvent e) {
                restoreSearch();
            }
        });
        topJP.add(search);

        Home.add(topJP,JLayeredPane.DEFAULT_LAYER);
    }

    //初始化窗口
    private void iniJF() {
        setTitle("笔记");
        setIconImage(new ImageIcon("src/cn/hniu/image/log.jpg").getImage());
        setSize(800, 1400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//居中
        setResizable(false);//禁止拉伸
        setLayout(null);
        Home.setLayout(null);
        Home.setBounds(0, 0, 800, 1400);

        detail.setLayout(null);
        detail.setBounds(0, 0, 800, 1400);

        ui.setSize(800, 1400);
        ui.add(Home, "Home");
        ui.add(detail, "detail");

        add(ui);
    }
    //点击清空
    private void clearSearch(){
        if(search.getText().equals("搜索笔记")){
            search.setText("");
        }
    }
    //移除恢复
    private void restoreSearch(){
        if(search.getText().trim().isEmpty()){
            search.setText("搜索笔记");
        }
    }
    //过滤
    private void searchNote() {
        ArrayList<Note> notes = action.getNotesByTitle(search.getText());
        updateData(notes);
    }
    //更新数据
    private void addClick(){
        initDetail(null);
        cardL.show(ui,"detail");
    }
    //初始化添加笔记按钮
    private void addNote(Note note){
        note.setTitle(deTitle.getText());
        note.setContent(deConcent.getText());

        boolean b = action.addNote(note);
        if(b){
            System.out.println("保存成功");
        }else{
            System.out.println("保存失败");
        }
        iniData();
    }
//打开详细界面
    private void openDetail(Note note){
        initDetail(note);
        cardL.show(ui,"detail");
    }
//初始化数据
    private void iniData(){
        ArrayList<Note> notes = action.getAllNotes();
        updateData(notes);
    }
//刷新数据
    private void updateData(ArrayList<Note> notes){
        home.removeAll();
        for (Note note : notes) {
            System.out.println(note.getTitle());
            home.add(createCard(note));
            home.add(Box.createVerticalStrut(10));
        }
        home.revalidate();
        home.repaint();
    }
//卡片
    private JPanel createCard(Note note) {
        JPanel card = new JPanel();
        card.setBorder(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setPreferredSize(new Dimension(740, 100));
        card.setMaximumSize(new Dimension(740, 100));  // 最大宽度给够，让它能横向撑满


        card.setBackground(Color.white);

        //笔记标题
        JLabel title = new JLabel(note.getTitle());
        title.setFont(new Font("宋体", Font.BOLD, 32));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        // 标题：上5 左15 下0 右0
        title.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 0));


        card.add(title);

        String content = note.getContent();
        String s = content.length() > 22 ? content.substring(0, 22) + "..." : content;
        JLabel text = new JLabel(s);
        text.setFont(new Font("宋体", Font.PLAIN, 24));
        text.setAlignmentX(Component.LEFT_ALIGNMENT);
        text.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 0));


        card.add(text);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openDetail(note);
            }
        });

        return card;


    }
//弹窗
    private void showMsg(String msg, String title) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void input() throws IOException, ExecutionException, InterruptedException {
        File file = showChooseFileZip();
        int err=0;
        if(file!=null){
            err=action.input(file);
            if(err==0){
                showMsg("导入成功","提示");
            }else{
                showMsg("导入失败"+err+"个文件","提示");
            }
        }
    }

    private void output() throws IOException {
        File file = showChooseFileDir();
        if(file!=null){
            action.output(file);
            showMsg("导出成功","提示");
        }
    }

    private File showChooseFileZip(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileNameExtensionFilter("zip文件", "zip"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    private File showChooseFileDir(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

}
