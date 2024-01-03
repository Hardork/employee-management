-- auto-generated definition
create database sqmanage;
use sqmanage;
DROP TABLE IF EXISTS `equipment`;
create table equipment
(
    equipmentID    bigint auto_increment
        primary key,
    personnelNo    varchar(256)                       not null comment '添加器材人员 职工',
    equipmentNo    varchar(256)                       not null comment '器材编号',
    equipmentName  varchar(256)                       null comment '器材名字',
    equipmentModel varchar(256)                       not null comment '器材型号',
    equipmentPrice double                             not null comment '器材价格',
    leftNum           int                                null default 0 comment '器材库存',
    purchaseDate   datetime default CURRENT_TIMESTAMP null comment '购买日期',
    `status`         tinyint  default 0                 null comment '是否可用 0可用 1不可用',
    createTime     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除 0正常 1删除'
)
    comment '器材表';
INSERT INTO equipment (personnelNo, equipmentNo, equipmentName, equipmentModel, equipmentPrice, leftNum, purchaseDate)
VALUES
    ('001', 'EQ001', '篮球', 'B-001', 100.00, 10, '2023-09-15'),
    ('002', 'EQ002', '足球', 'F-001', 80.00, 15, '2023-09-14'),
    ('003', 'EQ003', '网球拍', 'T-001', 150.00, 5, '2023-09-13'),
    ('001', 'EQ004', '乒乓球', 'P-001', 20.00, 50, '2023-09-12'),
    ('002', 'EQ005', '排球', 'V-001', 120.00, 8, '2023-09-11'),
    ('003', 'EQ006', '羽毛球拍', 'B-002', 200.00, 3, '2023-09-10'),
    ('001', 'EQ007', '跳绳', 'J-001', 10.00, 100, '2023-09-09'),
    ('002', 'EQ008', '瑜伽垫', 'Y-001', 60.00, 20, '2023-09-08'),
    ('003', 'EQ009', '哑铃', 'D-001', 50.00, 13, '2023-09-07'),
    ('001', 'EQ010', '飞盘', 'F-002', 30.00, 30, '2023-09-06');
-- auto-generated definition
DROP TABLE IF EXISTS `equipment_borrow`;
create table equipment_borrow
(
    borrowID          bigint auto_increment
        primary key,
    equipmentNo       varchar(256)                                       null comment '借用器材编号',
    borrowPersonnelNo varchar(256)                             not null comment '借用人员学号或教工号',
    leftNum           int                              default 1 comment '未归还数量',
    `status`            tinyint  default 0                 null comment '状态 0未批 1未通过 2借用中 3归还审批 4确认',
    `reason`          varchar(256)                      default '' null comment '未通过原因',
    borrowDate        datetime default CURRENT_TIMESTAMP           null comment '借用日期',
    specifiedDate     datetime default ((now() + interval 10 day)) null comment '规定归还日期',
    updateTime        datetime default CURRENT_TIMESTAMP           null comment '更新时间',
    createTime        datetime default CURRENT_TIMESTAMP           null comment '创建时间',
    isDelete          tinyint  default 0                           not null comment '是否删除 0正常 1删除'
)
    comment '器材借用表';



DROP TABLE IF EXISTS `equipment_borrow_record`;
create table equipment_borrow_record
(
    borrowRecordID          bigint auto_increment
        primary key,
    borrowID          bigint                                        not null comment '借用表id',
    borrowPersonnelNo varchar(256)                             not null comment '借用人员学号或教工号',
    outNum           int                                          not null comment '借出数量',
    personnelNo       varchar(256)                                 not null comment '审核人工号',
    updateTime        datetime default CURRENT_TIMESTAMP           null comment '更新时间',
    createTime        datetime default CURRENT_TIMESTAMP           null comment '创建时间',
    isDelete          tinyint  default 0                           not null comment '是否删除 0正常 1删除'

)
    comment '器材借出记录表';


DROP TABLE IF EXISTS `equipment_return_record`;
create table equipment_return_record
(
    returnRecordID          bigint auto_increment
        primary key,
    borrowPersonnelNo varchar(256)                             not null comment '借用人员学号或教工号',
    borrowID          bigint                                       not null comment '借用表id',
    returnNum         int                                          not null comment '归还数量',
    personnelNo       varchar(256)                                 not null comment '审核人',
    updateTime        datetime default CURRENT_TIMESTAMP           null comment '更新时间',
    createTime        datetime default CURRENT_TIMESTAMP           null comment '创建时间',
    isDelete          tinyint  default 0                           not null comment '是否删除 0正常 1删除'
)
    comment '器材归还表记录表';





DROP TABLE IF EXISTS `equipment_return`;
create table equipment_return
(
    returnID          bigint auto_increment
        primary key,
    borrowID          bigint                             not null comment '借用表id',
    borrowPersonnelNo varchar(256)                       not null comment '借用人员学号或教工号',
    returnDate        datetime  default CURRENT_TIMESTAMP    null comment '归还时间',
    returnNum           int    default 1                 not null comment '归还数量',
    `status`          tinyint default 0                  not null  comment '状态 0待审批 1通过 2未通过',
    penalty            double   default 0                   null comment '罚款',
    hasPenalty          boolean default false               null   comment '是否有罚款',
    hasLoss             boolean default false               null    comment '是否失踪',
    lossNum             int    default 0                 not null comment '报失数量',
    `reason`          varchar(1024)  default ''          null comment '未通过原因',
    updateTime        datetime default CURRENT_TIMESTAMP null comment '修改时间',
    isDelete          tinyint  default 0                 null comment '是否删除 0-未删除 1-删除',
    createTime        datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '器材归还申请表';

-- auto-generated definition
DROP TABLE IF EXISTS `personnel`;
create table personnel
(
    personnelID   bigint auto_increment
        primary key,
    personnelNo   varchar(256)                       null comment '学号或职工号',
    personnelName varchar(256)                       null comment '人员名字',
    personnelRole tinyint  default 0                 not null comment '人员角色 0学生 1教师',
    createTime    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除 0正常 1删除'
)
    comment '教师学生信息表';

-- 、宏畅、又铭、圣祥、、、、吉星、、、凯泽、琮晔
-- 所有的用户密码统一为 12345678
INSERT INTO personnel (personnelNo, personnelName, personnelRole)
VALUES
    ('3211310001', '张德华', 0),
    ('3211310002', '李又铭', 0),
    ('3211310003', '陈圣祥', 0),
    ('3211310004', '王玉华', 0),
    ('3211310005', '刘承恩', 0),
    ('1602370400', '林修远', 1),
    ('1602370401', '黄逸春', 1),
    ('1602370402', '吕明知', 1)
    ;


-- auto-generated definition
DROP TABLE IF EXISTS `user`;
create table user
(
    id           bigint auto_increment
        primary key,
    userAccount  varchar(256)                       null comment '账号 是学号或职工号',
    username     varchar(256)                       null comment '用户名 默认是教师学生的名字',
    userPassword varchar(512)                       null comment '密码',
    userStatus   int      default 0                 not null comment '状态 0正常 1封号',
    userRole     tinyint  default 0                 not null comment '0普通用户 1老师 2管理员',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除 0正常 1删除'
)
    comment '用户表';


DROP PROCEDURE IF EXISTS getDeptAttendanceRecords;
DELIMITER //

CREATE PROCEDURE getDeptAttendanceRecords(IN queryDate DATE, IN queryDeptID BIGINT)
BEGIN
    select td.deptID,td.deptName,e.employeeID,e.employeeName,
           ar.arID,ar.attendanceDate,ar.returnTime,ar.clockTime,
           isLate(clockTime) clockStatus, isEarlyDeparture(returnTime) returnStatus
    from
        (select deptName,deptID from dept where deptID = queryDeptID) td
            join dept_emp de on td.deptID = de.deptID
            and queryDate > de.fromDate and (de.toDate is null or queryDate < de.toDate)
            join employee e on de.employeeID = e.employeeID
            join attendance_record ar on e.employeeID = ar.employeeID
            and month(ar.attendanceDate) = month(queryDate);
END //

DELIMITER ;





