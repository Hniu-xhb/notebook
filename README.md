# 笔记本 (Notebook)

一个基于 Java Swing 的桌面笔记本应用。

## 功能

- 笔记列表展示
- 新建 / 保存笔记
- 搜索笔记（按标题过滤）
- 笔记详情编辑

## 技术栈

- **语言**: Java
- **UI**: Swing
- **数据存储**: 对象序列化文件（`.note`）
- **架构**: UI → Action → Service → DAO → File

## 项目结构

```
src/cn/hniu/
├── App.java              # 启动入口
├── action/NoteAction.java   # 控制器层
├── service/NoteService.java # 业务逻辑层
├── dao/NoteDAO.java      # 数据访问层
├── entity/Note.java      # 实体类
├── utils/FileUtils.java  # 文件读写工具
├── ui/HomeJF.java        # 主界面
├── image/log.jpg         # 应用图标
└── save/*.note           # 笔记数据文件
```

## 运行

```bash
javac -encoding UTF-8 -d out -sourcepath src src/cn/hniu/App.java
java -cp out cn.hniu.App
```
