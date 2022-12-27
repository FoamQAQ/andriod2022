package com.codenode.findmate.db;

public class UserInfo {
   public long rowid; // 行号
   public int xuhao; // 序号
   public String userName; // 姓名
   public String email; // email
   public String password; // 密码
   public String update_time; // 更新时间

   public UserInfo() {
      rowid = 0L;
      xuhao = 0;
      userName = "123456";
      email = "123456@gmail.com";
      password="123456";
      update_time = "";
   }
}
