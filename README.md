
项目结构

```yaml
demo:
	- config		
	- exception		# 异常处理模块
	- framework		# 业务逻辑所需的功能模块
	- modules:	#业务逻辑主模块
		- controller # 控制层
		- mapper	 # 数据库sql语句操作层
		- entity	 # 数据库表结构
		- data		 #方法结果的数据结构
		- params	 # 方法需要的参数结构
		- processors # 业务逻辑细节过程封装
		
```

