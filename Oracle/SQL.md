# SQL

## SQL定义：

1. SQL，指结构化查询语言，全称是 Structured Query Language 
2. SQL 是一种 ANSI（American National Standards Institute 美国国家标准化组织）标准的计算机语言。

## ANSI SQL语句分成以下六类（按使用频率排列）：

1. DQL (Data Query Language)数据查询语言 

   SELECT，WHERE，ORDER BY，GROUP BY和HAVING都用DQL的常用保留字  
   ` select * from tableName where id=1 `

2. DML（Data Manipulation Language）数据操作语言 
   INSERT，UPDATE和DELETE

   ```sql
     insert into ...
     update table ...
     delete table ...
   ```

3. DCL （Data Control Language）数据控制语言 
   GRANT、DENY、REVOKE
   某些RDBMS可用GRANT或REVOKE控制对表单个列的访问。

   ```plsql
   grant connect to cuihua;
   grant connect to cuihua with admin option;
   
   grant DBA to cuihua with admin option;
   -- 收回权限
   revoke connect from cuihua;
   revoke DBA from cuihua;
   ```

   

4. DDL （Data Definition Language）数据定义语言
   其语句包括动词CREATE和DROP

   ```sql
    CREATE INDEX --创建索引
    CREATE TABLE --创建一个数据库表 
    DROP TABLE --从数据库中删除表 
    ALTER TABLE --修改数据库表结构 
    CREATE VIEW --创建一个视图 
    DROP VIEW --从数据库中删除视图 
    CREATE INDEX --为数据库表创建一个索引 
    DROP INDEX --从数据库中删除索引 
    CREATE PROCEDURE --创建一个存储过程 
    DROP PROCEDURE --从数据库中删除存储过程 
    CREATE TRIGGER --创建一个触发器 
    DROP TRIGGER --从数据库中删除触发器 
    CREATE SCHEMA --向数据库添加一个新模式 
    DROP SCHEMA --从数据库中删除一个模式 
    CREATE DOMAIN --创建一个数据值域 
    ALTER DOMAIN --改变域定义 
    DROP DOMAIN --从数据库中删除一个域 
       
   ```

5. 指针控制语言（CCL）
   像DECLARE CURSOR，FETCH INTO和UPDATE WHERE CURRENT用于对一个或多个表单独行的操作。 

   ```plsql
    table1结构如下
    id    int
    name  varchar(50)
   
    declare @id int
    declare @name varchar(50)
    declare cursor1 cursor for         --定义游标cursor1
    select * from table1               --使用游标的对象(跟据需要填入select文)
    open cursor1                       --打开游标
    
    fetch next from cursor1 into @id,@name  --将游标向下移1行，获取的数据放入之前定义的变量@id,@name中
   ```

6. TPL  (Transaction Processing Language)事务处理语言 
   它的语句能确保被DML语句影响的表的所有行及时得以更新
   BEGIN TRANSACTION，COMMIT和ROLLBACK



## 参考文献：

http://www.cnblogs.com/westMin/p/5443255.html

http://www.phperz.com/article/16/0501/210968.html