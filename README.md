# mh4w
针对济南二机床集团公司数控机床公司的工人的工时统计程序

## 如何统计工人的工时
需要汇总两张表的信息：考勤表和工票表。由考勤表计算得出工人一个月的时间工作时间to，再计算等效工作时间te，其中te是根据以下规则计算的：
te = 1 * 正常上班时间 + 1.5 * 拖班时间 + 2.0 * 周末工作时间 + 3.0 * 法定节假日工作时间。
当工人在某一工作日缺勤的时候，需要从其周末、法定假日的工作时间减去相应的缺勤时间，并且补到工作时间中，即缺勤的时间需要从等效系数较高的法定节假日、周六周日中补偿，补偿的时间只有1倍的等效系数。

## 软件的功能
* 1.该软件可以自动的完成上述的统计算法，在软件中指定考勤表和工票表，并且手工填写周末和法定假日的日期，即可计算，计算得到的结果可以导出到xls中。
* 2.该软件可以自动检测某些表格中的错误，以防在指定表格时出现错误而造成统计结果的错误，受支持的错误检测有：
   * 格式检测：软件将检测表格中的格式，保证可以正确的读取。
   * 员工信息不一致检测：当检测到两个数据的员工工号相等，但部门或姓名不相等，软件会抛出失败信息，阻止进一步统计，以避免潜在的统计安全问题。
   * 员工信息不匹配检测：当检测到两个表所包含的员工集合不相等时，软件会抛出失败信息，阻止进一步统计，以避免潜在的安全问题。
   
## 软件的适应性
软件的很大部分的结构是通过配置完成的，虽然这些配置不能在软件中编辑，但可以通过外部的文本编辑器进行编辑。该软件可以进行以下的配置：
* 1.当考勤表或工票表发模板生改变时，可以通过调整配置来适应新的模板。
* 2.可以更改日志记录的样式
* 3.可以更改软件的语言，包括日志的语言和软件标签的语言
* 4.软件可以自定义班次，当班次的时间或整个班次发生改变，可以通过调整配置来适应新的班次。
* 5.软件可以自定义工票类型，当工票类型发生改变时，可以通过调整配置来适应新的工票类型。
