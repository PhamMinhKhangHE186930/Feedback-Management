create database FeedBack
go

use FeedBack
go

/**/
create table Account(
	id int primary key,
	[accountname] varchar(150),
	[password] varchar(150) not null,
	[role] int not null,
	displayname nvarchar(150),
	fullname nvarchar(150),
	dob date,
	gender bit,
	[address] nvarchar(150),
	phone varchar(11),
	email varchar(150)
)
go

create table FeedbackType(
	[id] int primary key,
	[typename] nvarchar(255),
)
go

create table Feedback(
	[id] int primary key,
	[typeid] int foreign key references FeedbackType(id),
	ftitle nvarchar(255),
	fcontent nvarchar(max),
	[status] bit,
	createdate datetime,
	[public] bit,
	accountid int foreign key references Account(id),
	[filename] nvarchar(max)
)
go


create table ResponseContent(
	id int,
	rtittle nvarchar(255),
	rcontent nvarchar(max),
	responsedate datetime,
	accountid int foreign key references Account(id),
	feedbackid int foreign key references Feedback(id),
	primary key (id, accountid, feedbackid),
	modifieddate datetime
)

create table Comment(
	id int,
	ccontent nvarchar(max),
	accountid int foreign key references Account(id),
	feedbackid int foreign key references Feedback(id),
	primary key (id, accountid, feedbackid)
)

create table StudentDetail(
	id int primary key,
	rollnumber varchar(8),
)

create table SupporterDetail(
	id int primary key,
	position varchar(150),
	department varchar(150)
)

create table StudentInformation(
	accountid int primary key,
	[sid] int foreign key references StudentDetail(id),
	FOREIGN KEY (accountid) REFERENCES Account(id)
)

create table SupporterInformation(
	accountid int primary key,
	[sid] int foreign key references SupporterDetail(id),
	FOREIGN KEY (accountid) REFERENCES Account(id)
)

/* ============================== INSERT DATA ======================== */
insert into Account(id, accountname, [password], [role], displayname)
values (1, 'support1', '123', 1, 'John Doe'),
(2, 'support2', '123', 1, 'Jane Smith'),
(3, 'support3', '123', 1, 'Mike Brown'),
(4, 'support4', '123', 1, 'Emily Jones'),
(5, 'student1', '123', 2, 'Chris Taylor'),
(6, 'student2', '123', 2, 'Amanda Robinson'),
(7, 'student3', '123', 2, 'Laura Jackson'),
(8, 'student4', '123', 2, 'Kevin Miller')

insert into FeedbackType
values (1, N'Phản hồi về cơ sở vật chất'),
(2, N'Phản hồi về môn học'),
(3, N'Phản hồi về sự kiện'),
(4, N'Khiếu nại'),
(5, N'Góp ý'),
(6, N'Yêu cầu hỗ trợ khác'),
(7, N'Phản hồi về chất lượng dịch vụ'),
(8, N'Phản hồi về đào tạo và hướng dẫn'),
(9, N'Phản hồi về cơ sở vật chất thư viện'),
(10, N'Phản hồi về chương trình học')

truncate table FeedbackType

insert into Feedback
values (1, 1, N'chất lượng ổ cắm', N'ổ cắm ở các phòng học tòa nhà beta thực sự kém chất lượng, hi vọng có thể sửa chữa và cải thiện thêm.', 0, '2024-06-14 09:19:13.320', 1, 2, null)


/* ============================== action on db ======================== */

select * from Account
select * from Feedback
select * from FeedbackType
select * from ResponseContent

delete from FeedbackType
where id between 1 and 10

select * from Feedback f, FeedbackType ft where f.typeid = ft.id and accountid = 5 and typeid = 9

insert into Feedback
values (4, 'type 2', N'lớp học', N'lớp học rộng hơn', 1, '2024-05-14 09:19:13.320', 0, 2)

SELECT a.*
FROM Account a
LEFT JOIN Feedback f ON a.id = f.accountid
where role = 2
ORDER BY COALESCE(f.createdate, '9999-12-31') DESC;

/*select all account order by latest feedback create*/
WITH LatestFeedback AS (
    SELECT
        f.accountid,
        MAX(f.createdate) AS latest_feedback_date
    FROM Feedback f
    GROUP BY f.accountid
)
SELECT a.*
FROM Account a
LEFT JOIN LatestFeedback lf ON a.id = lf.accountid
WHERE a.role = 2 and a.displayname like N'%a%'
ORDER BY COALESCE(lf.latest_feedback_date, '9999-12-31') DESC
OFFSET 0 ROWS 
FETCH NEXT 5 ROWS ONLY;

/**/

DECLARE @feedbackIdToCheck INT = 8
IF EXISTS (SELECT 1 FROM ResponseContent WHERE feedbackid = @feedbackIdToCheck)
    UPDATE Feedback SET [status] = 1 WHERE id = @feedbackIdToCheck;
ELSE
    UPDATE Feedback SET [status] = 0 WHERE id = @feedbackIdToCheck;

DECLARE @userIdToCheck INT = 8, @fileNameToCheck nvarchar(max) = 'a'
if exists (select 1 from Feedback where accountid = @userIdToCheck)
update

alter table Feedback
add [filename] nvarchar(max)

delete from Feedback where id = 15

ALTER TABLE Feedback
ALTER COLUMN fcontent nvarchar(max)

drop database FeedBack
drop table Feedback
drop table ResponseContent
