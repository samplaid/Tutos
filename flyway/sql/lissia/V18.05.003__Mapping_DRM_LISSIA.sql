/****** Object:  Table [dbo].[SAP_OPEN_BALANCE]    Script Date: 21/06/2018 08:12:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DRM_LISSIA_MAPPING](
	[DRM_LISSIA_MAPPING_ID] [numeric](20, 0) NOT NULL,
	[TYPE] [varchar](64) NULL,
	[DATA_IN] [varchar](64) NULL,
	[DATA_OUT] [varchar](64) NULL,
	[DESCRIPTION] [varchar](64) NULL,
	[STATUS] [numeric](1) NULL,
 CONSTRAINT [PK_DRM_LISSIA_MAPPING_ID] PRIMARY KEY CLUSTERED 
(
	[DRM_LISSIA_MAPPING_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO




