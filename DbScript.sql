CREATE DATABASE [IotDevices]
GO

USE [IotDevices]
GO

--create user to access the db
CREATE LOGIN iot_devices 
    WITH PASSWORD    = N'iot_dev',
    CHECK_POLICY     = OFF,
    CHECK_EXPIRATION = OFF;

ALTER SERVER ROLE [sysadmin] ADD MEMBER [iot_devices]

ALTER SERVER ROLE [dbcreator] ADD MEMBER [iot_devices]
--------------------------------------------------------------

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[sim_status](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_sim_status] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sim_card](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[operator_code] [nvarchar](150) NOT NULL,
	[country] [nvarchar](150) NULL,
	[status_id] [int] NULL,
 CONSTRAINT [PK_sim_card] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[operator_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[sim_card]  WITH CHECK ADD  CONSTRAINT [FK_sim_card_sim_status] FOREIGN KEY([status_id])
REFERENCES [dbo].[sim_status] ([id])
GO

ALTER TABLE [dbo].[sim_card] CHECK CONSTRAINT [FK_sim_card_sim_status]
GO

CREATE TABLE [dbo].[device](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sim_id] [int] NULL,
	[temperature] [int] NOT NULL,
 CONSTRAINT [PK_device] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[device]  WITH CHECK ADD  CONSTRAINT [FK_device_sim_card] FOREIGN KEY([sim_id])
REFERENCES [dbo].[sim_card] ([id])
GO

ALTER TABLE [dbo].[device] CHECK CONSTRAINT [FK_device_sim_card]
GO

INSERT INTO [dbo].[sim_status]
           ([name])
     VALUES
           ('Active'),
           ('WaitingForActivation'),
           ('Blocked'),
           ('Deactivated')
GO

INSERT INTO [dbo].[sim_card]
           ([operator_code]
           ,[country]
           ,[status_id])
     VALUES
           ('01013599299', 'Egypt', 1),
           ('01013599298', 'Albania', 2),
           ('01013599297', 'Algeria', 3),
           ('01013599296', 'Andorra', 4),
           ('01013599295', 'Argentina', 4),
           ('01013599294', 'Armenia', 2),
           ('01013599293', 'Egypt', 2),
           ('01013599292', 'England', 2),
           ('01013599291', 'Australia', 3),
           ('01013599290', 'Austria', 2),
           ('01013599280', 'Barbados', 1),
           ('01013599281', 'Egypt', 2),
           ('01013599282', 'Italy', 1),
           ('01013599283', 'Egypt', 2),
           ('01013599284', 'Brazil', 2),
           ('01013599285', 'Egypt', 2),
           ('01013599286', 'Egypt', 2),
           ('01013599287', 'Egypt', 3),
           ('01013599288', 'Egypt', 2),
           ('01013599289', 'Egypt', 1)
GO

INSERT INTO [dbo].[device]
           ([sim_id]
           ,[temperature])
     VALUES
           (1, 24),
           (2, 25),
           (3, 80),
           (4, 24),
           (5, 28),
           (6, 90),
           (7, 25),
           (8, 29),
           (9, 40),
           (10, 50),
           (11, 66),
           (12, 44),
           (13, 27),
           (14, 28),
           (15, 24),
           (16, 25)
GO



