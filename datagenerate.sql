USE [FeedBack]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 18/07/2024 1:42:25 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[id] [int] NOT NULL,
	[accountname] [varchar](150) NULL,
	[password] [varchar](150) NOT NULL,
	[role] [int] NOT NULL,
	[displayname] [nvarchar](150) NULL,
	[fullname] [nvarchar](150) NULL,
	[dob] [date] NULL,
	[gender] [bit] NULL,
	[address] [nvarchar](150) NULL,
	[phone] [varchar](11) NULL,
	[email] [varchar](150) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 18/07/2024 1:42:25 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedback](
	[id] [int] NOT NULL,
	[typeid] [int] NULL,
	[ftitle] [nvarchar](255) NULL,
	[fcontent] [nvarchar](max) NULL,
	[status] [bit] NULL,
	[createdate] [datetime] NULL,
	[public] [bit] NULL,
	[accountid] [int] NULL,
	[filename] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FeedbackType]    Script Date: 18/07/2024 1:42:25 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FeedbackType](
	[id] [int] NOT NULL,
	[typename] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ResponseContent]    Script Date: 18/07/2024 1:42:25 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ResponseContent](
	[id] [int] NOT NULL,
	[rtittle] [nvarchar](255) NULL,
	[rcontent] [nvarchar](max) NULL,
	[responsedate] [datetime] NULL,
	[accountid] [int] NOT NULL,
	[feedbackid] [int] NOT NULL,
	[modifieddate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC,
	[accountid] ASC,
	[feedbackid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (1, N'support1', N'123', 1, N'John Doe', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (2, N'support2', N'123', 1, N'Jane Smith', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (3, N'support3', N'123', 1, N'Mike Brown', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (4, N'support4', N'123', 1, N'Emily Jones', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (5, N'student1', N'123', 2, N'Chris Taylor', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (6, N'student2', N'123', 2, N'Amanda Robinson', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (7, N'student3', N'123', 2, N'Laura Jackson', NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [accountname], [password], [role], [displayname], [fullname], [dob], [gender], [address], [phone], [email]) VALUES (8, N'student4', N'123', 2, N'Kevin Miller', NULL, NULL, NULL, NULL, NULL, NULL)
GO
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (1, 9, N'Chất lượng thư viện', N'Thư viện không thể giữ trật tự, rất ồn ào đặc biệt khi có luk hoặc một số sự kiện nhạc cụ dân tộc ở trường', 0, CAST(N'2024-07-12T01:25:50.657' AS DateTime), 1, 5, NULL)
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (2, 9, N'Chất lượng thư viện', N'Thư viện không thể giữ trật tự, rất ồn ào đặc biệt khi có luk hoặc một số sự kiện nhạc cụ dân tộc ở trường', 0, CAST(N'2024-07-12T01:27:30.027' AS DateTime), 1, 5, NULL)
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (3, 1, N'Nhà xe', N'Nhà xe quá chật', 1, CAST(N'2024-07-12T01:44:30.757' AS DateTime), 1, 5, NULL)
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (4, 6, N'Xin miễn điểm danh', N'Do đã tìm được hợp đồng chính thức của công ty XXX, em xin giấy xác nhận miễn điểm danh để đi làm.', 1, CAST(N'2024-07-12T02:54:05.063' AS DateTime), 0, 5, N'FileText.docx')
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (5, 7, N'Đồ ăn ở căng tin', N'Đồ ăn ở căng tin trường không đảm bảo', 0, CAST(N'2024-07-12T03:01:15.960' AS DateTime), 1, 6, N'84a38b7c-ac3c-4e4a-ad2a-10b5a0533388.jpg')
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (6, 3, N'Sự kiện FCamp', N'Tổ chức vào thời gian đi học của các sinh viên khác gây ồn ào, ảnh hưởng đến quá trình học tập của sinh viên', 0, CAST(N'2024-07-12T03:37:29.577' AS DateTime), 0, 6, NULL)
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (7, 1, N'CSVC', N'điều hòa không tốt', 0, CAST(N'2024-07-12T08:00:56.713' AS DateTime), 1, 5, N'FileText(1).docx')
INSERT [dbo].[Feedback] ([id], [typeid], [ftitle], [fcontent], [status], [createdate], [public], [accountid], [filename]) VALUES (8, 5, N'adfg', N'sdfg', 0, CAST(N'2024-07-17T11:09:32.487' AS DateTime), 1, 5, NULL)
GO
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (1, N'Phản hồi về cơ sở vật chất')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (2, N'Phản hồi về môn học')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (3, N'Phản hồi về sự kiện')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (4, N'Khiếu nại')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (5, N'Góp ý')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (6, N'Yêu cầu hỗ trợ khác')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (7, N'Phản hồi về chất lượng dịch vụ')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (8, N'Phản hồi về đào tạo và hướng dẫn')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (9, N'Phản hồi về cơ sở vật chất thư viện')
INSERT [dbo].[FeedbackType] ([id], [typename]) VALUES (10, N'Phản hồi về chương trình học')
GO
INSERT [dbo].[ResponseContent] ([id], [rtittle], [rcontent], [responsedate], [accountid], [feedbackid], [modifieddate]) VALUES (1, N'Chấp nhận đơn miễn điểm danh', N'Chúng tôi đã nhận được file hợp đồng và quyết định cấp cho bạn giấy xác nhận miễn điểm danh. Trong hai ngày tới bạn có thể lên nhận giấy.', CAST(N'2024-07-12T03:14:56.760' AS DateTime), 1, 4, CAST(N'2024-07-12T03:14:56.760' AS DateTime))
INSERT [dbo].[ResponseContent] ([id], [rtittle], [rcontent], [responsedate], [accountid], [feedbackid], [modifieddate]) VALUES (2, N'abc', N'abc', CAST(N'2024-07-12T08:08:05.270' AS DateTime), 1, 3, CAST(N'2024-07-12T08:08:05.270' AS DateTime))
INSERT [dbo].[ResponseContent] ([id], [rtittle], [rcontent], [responsedate], [accountid], [feedbackid], [modifieddate]) VALUES (3, N'ẻt', N'df', CAST(N'2024-07-17T11:11:41.213' AS DateTime), 1, 3, CAST(N'2024-07-17T11:11:41.213' AS DateTime))
GO
ALTER TABLE [dbo].[Feedback]  WITH CHECK ADD FOREIGN KEY([accountid])
REFERENCES [dbo].[Account] ([id])
GO
ALTER TABLE [dbo].[Feedback]  WITH CHECK ADD FOREIGN KEY([typeid])
REFERENCES [dbo].[FeedbackType] ([id])
GO
ALTER TABLE [dbo].[ResponseContent]  WITH CHECK ADD FOREIGN KEY([accountid])
REFERENCES [dbo].[Account] ([id])
GO
ALTER TABLE [dbo].[ResponseContent]  WITH CHECK ADD FOREIGN KEY([feedbackid])
REFERENCES [dbo].[Feedback] ([id])
GO
