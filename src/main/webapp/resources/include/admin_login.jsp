<%@ page contentType="text/html; charset=UTF-8"%>
   <!-- 본문 내용 -->
    
     <div id="left_menu">      
            <!-- 본문내용에 로그인 환영 메시지 삽입 -->
       <div id="aMain_Login">             
         <form method="post" action="admin_Logout.do">
           <table id="aLogin_t">
             <tr>
              <td  align="center">
                 <b>${admin_id}관리자 님 로그인을 환영합니다.</b></td>
             </tr>
             <tr> <td> &nbsp;</td> </tr>
             <tr> 
              <td align="center">
                <input type="submit" value="로그아웃" class="admin_b"/>
              </td>
             </tr>
           </table>
         </form>      
       </div>
     </div>