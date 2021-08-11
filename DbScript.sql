CREATE DATABASE [IotDevices]

USE [IotDevices]
GO

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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[operator_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
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

