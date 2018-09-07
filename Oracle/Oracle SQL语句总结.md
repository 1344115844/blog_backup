# Oracle SQL语句总结

根据ANSI SQL进行分类。不清楚ANSI SQL标准的，请看链接https://blog.csdn.net/qq_37933685/article/details/81407224

## DDL数据定义语言  

### 创建和删除用户

```plsql
/*添加Oracle用户*/
create user cuihua
identified by 1234
default tablespace cuihua
temporary tablespace temp
/*删除Oracle用户*/
--对于单个user和tablespace 来说， 可以使用如下命令来完成。
drop user cuihua cascade--删除了user，只是删除了该user下的schema objects，是不会删除相应的tablespace的。
DROP TABLESPACE abc INCLUDING CONTENTS AND DATAFILES;--删除表空间
```

### 创建和删除表空间

```plsql
/*创建表空间*/
  create tablespace cuihua           -- 指定表空间的名字
  datafile 'E:\orcl\space\cuihua.dbf'    -- 指定表空间的位置
  size 20M    -- 指定表空间的大小（会自动发生变化的）
  autoextend on;  -- 设置自动扩容
/*删除表空间*/
	drop tablespace cuihua INCLUDING CONTENTS and DATAFILES
```

### 创建、删除和清空表

```plsql
--创建表
create table tb_user(
       user_name varchar2 (50),
       user_age int,
       user_sex varchar2(4)
)
--删除表
drop table text_import
--清空表
DELETE FROM text_import;--对于有少量行记录的表，DELETE语句做得很好。 但是，当拥有大量行记录的表时，使用DELETE语句删除所有数据效率并不高。
TRUNCATE TABLE table_name;--同样是清空表
--如果表通过外键约束与其他表有关系，则需要使用CASCADE子句：
TRUNCATE TABLE table_name CASCADE;--级联删除
  
```

默认情况下，要从表中删除所有行，请指定要在TRUNCATE TABLE子句中截断的表的名称：
TRUNCATE TABLE table_name 语法扩展

```plsql
 TRUNCATE TABLE schema_name.table_name
[CASCADE]
[[ PRESERVE | PURGE] MATERIALIZED VIEW LOG ]]
[[ DROP | REUSE]] STORAGE ]
```

在这种情况下，TRUNCATE TABLE CASCADE语句删除table_name表中的所有行，并**递归地截断链中的关联表**。
请注意，TRUNCATE TABLE CASCADE语句需要使用**ON DELETE CASCADE**子句定义的**外键约束**才能工作。
通过**MATERIALIZED VIEW LOG**子句，可以指定在表上定义的物化视图日志是否在截断表时被保留或清除。 **默认情况下**，物化视图日志被保留。
**STORAGE**子句允许选择删除或重新使用由截断行和关联索引(如果有的话)释放的存储。 **默认情况下**，存储被删除。

> 请注意，要截断表，它必须在您自己的模式中，或者必须具有DROP ANY TABLE系统权限。

### 加锁和解锁账户

```plsql
-- 如果某个用户被加锁或者解锁
alter user cuihua account lock; -- 加锁
alter user cuihua account unlock; -- 解锁
```

### 改变表结构

```plsql
/*ALTER TABLE语句可用来：*/
--1. 添加一个列或多个列
alter table text_import add  (mynewcolumn  int)
--2. 修改列的属性
alter table text_import modify (mynewcolumn varchar2(2))
--3. 删除列
alter table text_import drop column mynewcolumn
--4. 重命名列
ALTER TABLE text_import
RENAME COLUMN pid TO  passengerid;
--5. 重命名表
alter table passenger rename to  text_import
```



## DCL数据控制语言  

```plsql

-- 如果添加了新的用户，则需要赋予连接权限
grant connect to cuihua;
grant DBA to cuihua with admin option;
-- 收回权限
revoke connect from cuihua;
revoke DBA from cuihua;
```



## DML数据操作语言  

### 插入操作

```plsql
insert into tb_user values('suveng',22,'男')
```

### 更新操作

```plsql
update tb_user set user_name='苏' where user_sex='男'
```

### 删除操作

```plsql
delete from tb_user where (user_sex!='男' and user_sex！='女')or user_sex is null
```

## DQL数据查询语言 

```plsql
select * from tb_user
```

