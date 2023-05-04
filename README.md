# WebChat_Student_course_attendance
基于微信小程序学生课程考勤系统后端SSM可升级SpringBoot毕业源码案例设计

## 开发技术：微信小程序客户端 + Java后台服务器端 + mysql数据库

  项目一共2个身份，用户和管理员。学生在小程序客户端绑定手机号登录后，可以首页查看新闻公告信息，可以查询课程信息，用户选择自己的课程开始提交考勤信息，选择要考勤的日期及时段提交考勤记录，后端考勤处理业务会自动判断考勤是否有重复提交冲突，如果没有冲突就提交成功，用户可以查询自己的考勤信息，发布留言及管理自己的留言，修改个人信息等。管理员登录后端后可以对所有信息进行管理，包括用户管理，课程管理，考勤记录管理，系统参数管理，留言回复管理，新闻公告管理等待。

### 实体ER属性：
用户: 用户名,登录密码,姓名,性别,出生日期,用户照片,联系电话,邮箱,家庭地址,注册时间,微信openid
课程类型: 课程类型id,课程类型名称,课程类型说明
课程: 课程id,课程类型,课程名称,课程照片,总课时,上课教室,课程学分,详细介绍,发布时间
考勤信息: 订单编号,考勤学生,考勤课程,考勤日期,考勤时段,考勤状态,考勤时间,考勤备注
时段: 时段id,时段名称
留言: 留言id,留言标题,留言内容,留言人,留言时间,管理回复,回复时间
新闻公告: 公告id,标题,公告内容,发布时间
