<%@page import="java.util.Random"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<%@ page import="org.apache.poi.poifs.dev.*" %>
<%@ page import="org.apache.poi.hssf.record.*" %>
<%@ page import="org.apache.poi.hssf.model.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>
<%@ page import="org.apache.poi.hssf.util.*" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>엑셀 POI </title>
</head>
<body>
 
 
 
<%    
int bbs = (Integer)request.getAttribute("bbscount");
int board = (Integer)request.getAttribute("boardcount");
int gongji = (Integer)request.getAttribute("gongjicount");
int guest = (Integer)request.getAttribute("guestcount");

// 엑셀 저장 파일명
Calendar c=Calendar.getInstance();
int year=c.get(Calendar.YEAR);//년도값
int month=c.get(Calendar.MONTH)+1;//월값
int date=c.get(Calendar.DATE);//일값
int second = c.get(Calendar.SECOND); // 초

String sFileName = "excel_"+year+"-"+month+"-"+date+"-"+second+".xls";
sFileName = new String (sFileName.getBytes("UTF-8"), "8859_1");

out.clear();
out = pageContext.pushBody();
response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.
 
 
 
String strClient = request.getHeader("User-Agent");
 
String fileName = sFileName;
 
if (strClient.indexOf("MSIE 5.5") > -1) {
 //response.setContentType("application/vnd.ms-excel");
 response.setHeader("Content-Disposition", "filename=" + fileName + ";");
} else {
 response.setContentType("application/vnd.ms-excel");
 response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
}
 
OutputStream fileOut = null;
 
//---------------------------------------------------------------
// 엑셀 처리 시작 - 시트명 지정 
HSSFWorkbook objWorkBook = new HSSFWorkbook();                 // workbook  객체 생성
HSSFSheet objSheet = objWorkBook.createSheet("Board Result");  // worksheet 객체 생성
HSSFRow objRow = null;                                         // Row  객체 생성
HSSFCell objCell = null;                                       // Cell 객체 생성
 
//-----------------------------------------------------------------

// 스타일 설정
 
// 스타일 객체 생성
HSSFCellStyle styleHd = objWorkBook.createCellStyle();         // Life Design   스타일
HSSFCellStyle styleTot = objWorkBook.createCellStyle();
HSSFCellStyle styleSub = objWorkBook.createCellStyle();        // "소계1"         
HSSFCellStyle styletitle1 = objWorkBook.createCellStyle();     // "Title : 고정지출"  
HSSFCellStyle styletitle2 = objWorkBook.createCellStyle();     // "Title : 변동지출"
HSSFCellStyle styletitle3 = objWorkBook.createCellStyle();     // "고정수입 & 변동수입"
HSSFCellStyle styleBody1 = objWorkBook.createCellStyle();      // "번호/항목/금액" 
HSSFCellStyle styleBody2 = objWorkBook.createCellStyle();      // "항목값"에만 해당 
HSSFCellStyle styleBody3 = objWorkBook.createCellStyle();      // "번호값,금액값"에만 해당
HSSFCellStyle styleDate = objWorkBook.createCellStyle();       // "날짜"         

// 제목 폰트
HSSFFont font = objWorkBook.createFont();
font.setFontHeightInPoints((short)18);
font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
font.setFontName("맑은 고딕"); // 글꼴
font.setColor(HSSFColor.GREEN.index);
 
// 제목 스타일에 폰트 적용, 정렬, 색깔
styleHd.setFont(font);
styleHd.setBorderBottom(HSSFCellStyle.BORDER_THICK);
styleHd.setBottomBorderColor(HSSFColor.BLACK.index);
styleHd.setBorderLeft(HSSFCellStyle.BORDER_THICK);
styleHd.setLeftBorderColor(HSSFColor.BLACK.index);
styleHd.setBorderRight(HSSFCellStyle.BORDER_THICK);
styleHd.setRightBorderColor(HSSFColor.BLACK.index);
styleHd.setBorderTop(HSSFCellStyle.BORDER_THICK);
styleHd.setTopBorderColor(HSSFColor.BLACK.index);
styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
styleHd.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
styleHd.setFillForegroundColor(HSSFColor.YELLOW .index);
styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

//-----------------------------------------------------------------

// "총계" 폰트
HSSFFont font2 = objWorkBook.createFont();
font2.setFontHeightInPoints((short)14);
font2.setBoldweight((short)font.BOLDWEIGHT_BOLD);
font2.setFontName("맑은 고딕"); // 글꼴


//"총계" 스타일에 폰트 적용, 정렬, 색깔
styleTot.setFont(font2);
styleTot.setBorderBottom(HSSFCellStyle.BORDER_THIN);
styleTot.setBottomBorderColor(HSSFColor.BLACK.index);
styleTot.setBorderLeft(HSSFCellStyle.BORDER_THIN);
styleTot.setLeftBorderColor(HSSFColor.BLACK.index);
styleTot.setBorderRight(HSSFCellStyle.BORDER_THIN);
styleTot.setRightBorderColor(HSSFColor.BLACK.index);
styleTot.setBorderTop(HSSFCellStyle.BORDER_THIN);
styleTot.setTopBorderColor(HSSFColor.BLACK.index);
styleTot.setAlignment (HSSFCellStyle.ALIGN_CENTER);
styleTot.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
styleTot.setFillForegroundColor(HSSFColor.ORANGE.index);
styleTot.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
styleTot.setWrapText(true); 

// 소계 폰트
HSSFFont font3 = objWorkBook.createFont();
font3.setFontHeightInPoints((short)14);
font3.setBoldweight((short)font.BOLDWEIGHT_BOLD);
font3.setFontName("맑은 고딕"); // 글꼴
font3.setColor(HSSFColor.WHITE.index);

// "소계" 스타일에 폰트 적용, 정렬, 색깔
styleSub.setFont(font3);
styleSub.setBorderBottom(HSSFCellStyle.BORDER_THIN);
styleSub.setBottomBorderColor(HSSFColor.BLACK.index);
styleSub.setBorderLeft(HSSFCellStyle.BORDER_THIN);
styleSub.setLeftBorderColor(HSSFColor.BLACK.index);
styleSub.setBorderRight(HSSFCellStyle.BORDER_THIN);
styleSub.setRightBorderColor(HSSFColor.BLACK.index);
styleSub.setBorderTop(HSSFCellStyle.BORDER_THIN);
styleSub.setTopBorderColor(HSSFColor.BLACK.index);
styleSub.setAlignment (HSSFCellStyle.ALIGN_CENTER);
styleSub.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
styleSub.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
styleSub.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
styleSub.setWrapText(true); 
//-----------------------------------------------------------------

//"자료실, 게시판,공지사항,방명록 타이틀
HSSFFont font4 = objWorkBook.createFont();
font4.setFontHeightInPoints((short)14);
font4.setBoldweight((short)font.BOLDWEIGHT_BOLD);
font4.setFontName("맑은 고딕"); // 글꼴

//"자료실, 게시판,공지사항,방명록 - 폰트 적용, 정렬, 색깔
styletitle1.setFont(font4);
styletitle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
styletitle1.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
styletitle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
styletitle1.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
styletitle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
styletitle1.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
styletitle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
styletitle1.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
styletitle1.setAlignment (HSSFCellStyle.ALIGN_CENTER);
styletitle1.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
styletitle1.setFillForegroundColor(HSSFColor.GOLD.index);
styletitle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


//-----------------------------------------------------------------

// "단위 : 단원"
HSSFFont font5 = objWorkBook.createFont();
font5.setFontHeightInPoints((short)12);
font5.setBoldweight((short)font.BOLDWEIGHT_BOLD);

// "번호/항목/금액" 스타일에 폰트 적용, 정렬, 색깔
styleBody1.setFont(font5);
styleBody1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
styleBody1.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
styleBody1.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody1.setBorderRight(HSSFCellStyle.BORDER_THIN);
styleBody1.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody1.setBorderTop(HSSFCellStyle.BORDER_THIN);
styleBody1.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody1.setAlignment (HSSFCellStyle.ALIGN_CENTER);
styleBody1.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
styleBody1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
styleBody1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//-----------------------------------------------------------------

// "금액값" 폰트
HSSFFont font6 = objWorkBook.createFont();
font6.setFontHeightInPoints((short)12);
font6.setBoldweight((short)font.BOLDWEIGHT_BOLD);
font6.setFontName("맑은 고딕"); // 글꼴

// "번호값, 금액값" 스타일에 폰트 적용, 정렬, 색깔
styleBody2.setFont(font6);
styleBody2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
styleBody2.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
styleBody2.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody2.setBorderRight(HSSFCellStyle.BORDER_THIN);
styleBody2.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody2.setBorderTop(HSSFCellStyle.BORDER_THIN);
styleBody2.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
styleBody2.setAlignment (HSSFCellStyle.ALIGN_CENTER);
styleBody2.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
 
//-------------------------------------------------------

// 길이 설정
objSheet.setColumnWidth((int)0,(int)2800);
objSheet.setColumnWidth((int)1,(int)5000);
objSheet.setColumnWidth((int)2,(int)2500);
objSheet.setColumnWidth((int)3,(int)700);
objSheet.setColumnWidth((int)4,(int)2800);
objSheet.setColumnWidth((int)5,(int)5000);
objSheet.setColumnWidth((int)6,(int)2500);

// 1행 - 제목 : 0행 0열이 기준점
objSheet.addMergedRegion(new Region(0,(short)0,0,(short)5));  // 셀 병합
objRow = objSheet.createRow((short)0);
objRow.setHeight ((short) 0x200);      
objCell = objRow.createCell((short)0);
objCell.setCellValue("게시물 현황 결과 보고서");                 // 보고서 Title
objCell.setCellStyle(styleHd);
objCell = objRow.createCell((short)1);
objCell.setCellStyle(styleHd);
objCell = objRow.createCell((short)2);
objCell.setCellStyle(styleHd);
objCell = objRow.createCell((short)3);
objCell.setCellStyle(styleHd);
objCell = objRow.createCell((short)4);
objCell.setCellStyle(styleHd);
objCell = objRow.createCell((short)5);
objCell.setCellStyle(styleHd);

// 2행 - 빈 공간 & 통합
objSheet.addMergedRegion(new Region(1,(short)0,1,(short)5));  // 셀 병합
objRow = objSheet.createRow((short)1);
objCell = objRow.createCell((short)0);

// 3행 - 빈 공간 & 병합
objSheet.addMergedRegion(new Region(2,(short)0,2,(short)6));  
objRow = objSheet.createRow((short)2);
objCell = objRow.createCell((short)0);

// 4행-------------------------------------------------------
objRow = objSheet.createRow((short)3); // 3행
objCell = objRow.createCell((short)0); // 1열
objCell.setCellValue("총   계");
objCell.setCellStyle(styleTot);
objCell = objRow.createCell((short)1);
objCell.setCellValue((bbs+board+gongji+guest));//총 수
objCell.setCellStyle(styleBody2);
objCell = objRow.createCell((short)5);
objCell.setCellValue("(단위 : 개수)");
objCell.setCellStyle(styleBody1);
 
// 5행 - 빈 공간 & 병합
objSheet.addMergedRegion(new Region(4,(short)0,4,(short)6));  
objRow = objSheet.createRow((short)4);
objCell = objRow.createCell((short)0);

//6행 - 빈 공간 & 병합
objSheet.addMergedRegion(new Region(5,(short)0,5,(short)6));  
objRow = objSheet.createRow((short)5);
objCell = objRow.createCell((short)0);

// 7행-------------------------------------------------------
objRow = objSheet.createRow((short)6);
objCell = objRow.createCell((short)0);
objCell.setCellValue("자료실");
objCell.setCellStyle(styletitle1);

objCell = objRow.createCell((short)1);
objCell.setCellValue(bbs); // 자료실 수
objCell.setCellStyle(styleBody2);
 
objCell = objRow.createCell((short)4);
objCell.setCellValue("게시판");
objCell.setCellStyle(styletitle1);

objCell = objRow.createCell((short)5);
objCell.setCellValue(board); // 게시판 수
objCell.setCellStyle(styleBody2); 

// 8행-------------------------------------------------------
objRow = objSheet.createRow((short)7);
objCell = objRow.createCell((short)0);
objCell.setCellValue("공지사항");
objCell.setCellStyle(styletitle1);

objCell = objRow.createCell((short)1);
objCell.setCellValue(gongji); // 공지사항 수
objCell.setCellStyle(styleBody2);

objCell = objRow.createCell((short)4);
objCell.setCellValue("방명록");
objCell.setCellStyle(styletitle1);

objCell = objRow.createCell((short)5);
objCell.setCellValue(guest); // 방명록 수
objCell.setCellStyle(styleBody2);

// 9행-------------------------------------------------------
objRow = objSheet.createRow((short)8);
objCell = objRow.createCell((short)0);
objCell.setCellValue("소   계");
objCell.setCellStyle(styleSub);
objCell = objRow.createCell((short)1);
objCell.setCellValue(bbs+gongji);
objCell.setCellStyle(styleBody2);

//9행-------------------------------------------------------
objCell = objRow.createCell((short)4);
objCell.setCellValue("소   계");
objCell.setCellStyle(styleSub);
objCell = objRow.createCell((short)5);
objCell.setCellValue(board+guest);
objCell.setCellStyle(styleBody2);

// 10행 - 빈 공간 & 병합
objSheet.addMergedRegion(new Region(9,(short)0,9,(short)6));  
objRow = objSheet.createRow((short)9);
objCell = objRow.createCell((short)0);


out.clear(); 
out = pageContext.pushBody();
 
fileOut = response.getOutputStream(); 
objWorkBook.write(fileOut);
fileOut.close();
  
%> 

</body>
</html>
