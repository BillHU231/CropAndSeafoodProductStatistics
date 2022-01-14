
# 載入農委會openData API project
> # 簡介
>> 爬取農委會提供的OpenData(https://data.coa.gov.tw/api.aspx )來進行農產品和漁產品的分析與統計。
> ## 分析項目
>> ### Demo1
>>> **統計指定時間區間,農產品在指定市場的的平均交易量、最大交易量、最低交易量**
>> ### Demo2
>>> **指定日期來統計農產品和漁產品整日的交易量**
>> ### Demo3
>>> **指定日期和產品判斷過去五天的交易量是否遵守嚴格遞增**
>> ### Demo4
>>> **輸入指定年月,判斷此月最暢銷前10名的產品**    


# 設計方向
> ## 爬取資料
>> - ### 1.爬取產品資料-分別爬取農糧署的API農產品資料型別和漁產品交易資料型別進入Product資料表中,並有個欄位來分辨是農產品還是漁產品
>>> ![productimage](https://github.com/BillHU231/systex_lab2_billhu_v3/blob/master/images/product.JPG)
>> - ### 2.爬取市場資料-分別爬取農糧署的API農產市場資料型別資料型別和漁市場資料型別進入Market資料表中,並有個欄位來分辨是農市場還是漁市場
>>> ![marketimage](https://github.com/BillHU231/systex_lab2_billhu_v3/blob/master/images/market.JPG)
>> - ### 3.爬取交易行情資料-爬取農糧署的API中農產品和漁產品交易行情,分別給交易日期和市場代號參數,日期利用部分java.time.LocalDate.now()取的現在時間的年、月、日,在利用三層迴圈製作2020-01-01至當天時間的日期的參數,市場代號參數是直接抓取Market資料表中的市場代號,並利用兩個參數依序爬取交易行情進Traded資料表
>>> ![tradedimage](https://github.com/BillHU231/systex_lab2_billhu_v3/blob/master/images/traded.jpg) 
> ## 資料表設計
>> - **1.Product資料表:分別有Productid位欄位為primarykey,並自動產生,和ProductCode和ProductName和Category**
>> - **2.Market資料表:分別有Marketid位欄位為primarykey,並自動產生,和MarketCode和MarkettName和Category**
>> - **3.Traded資料表:分別有Tradedid位欄位為primarykey,並自動產生,和TradedDate和ProductId和MarketId和Category和AvgPrice和TransQuantity,其中ProductId為FOREIGN KEY和Product資料表中的id為多對一的關係MarketId為FOREIGN KEY和Market資料表中的id為多對一的關係**
>> - **這樣設計資料表有利於交易行情資料表的查詢效率**
> ## 取得建立資料表SQL語法
>> - 路徑:src\main\resources\schema.sql
> ## 取得SQL Schema 
>> - 路徑:DOC\SQL Schema.xlsx

> ## 資料表ED圖
>> ![sqlEDwj6](https://github.com/BillHU231/systex_lab2_billhu_v2/blob/master/images/SQL_ER.PNG)
> ##  sampleData
>> - 因檔案過大,另附雲端:https://drive.google.com/drive/folders/1-va-ds8ODXYL1OEyQYZ6KFum2brvFKqP?usp=sharing
> ## Demo1設計方向
>> - **1.在Product資料庫利用ProductName取的id**
>> - **2.在Market資料庫利用MarketName取的id**
>> - **3.在利用SQL語法給日期區間和Productid和Marketid在Traded資料表取得平均交易量、最大交易量、最低交易量**
>> ### 流程圖
>>> ![Demo1image](https://github.com/BillHU231/systex_lab2_billhu_v3/blob/master/images/Demo1.PNG) 
> ## Demo2設計方向
>> - **1.利用Traded資料表中Category 分別可以取得指定日期的農和漁產品的交易金額,在利用SQL中SUM()的語法算出農和漁的交易總金額**
>> ### 流程圖
>>> ![Demo2image](https://github.com/BillHU231/systex_lab2_billhu_v3/blob/master/images/Demo2.PNG) 
> ## Demo3設計方向
>> - **1.日期處理:利用java.util.Calendar的class中的方法可以取的指定日期的前五天**
>> -  **2.判斷是否嚴格遞增:取出的五天的交易量,分別依序(日期由過去到現在)裝進TreeSet和ArrayList,因為TreeSet會升冪排序,ArrayList會依照日期排序,在比較TreeSet和ArrayList中依序的值是否相同,就可以知道是否嚴格遞增**
>> ### 流程圖
>>> ![Demo3image](https://github.com/BillHU231/systex_lab2_billhu_v3/blob/master/images/Demo3.JPG) 
> ## Demo4設計方向
>> - **1.指定日期區間,在利用sql語法GROUP BY 來分組產品 ,就可以算出每個產品的總交易量,在利用sql語法中ORDER BY 來降冪排序每個產品的總交易量,在取出前10筆**
>> ### 流程圖
>>> ![Demo4image](https://github.com/BillHU231/systex_lab2_billhu_v3/blob/master/images/Demo4.JPG) 
> ## Demo的sql語法
>  - Demo1
```
SELECT AVG(transquantity) ,MAX(transquantity),MIN(transquantity) FROM Traded WHERE TradedDate BETWEEN ? AND ? AND productid= ? AND marketid= ?;
```
> - Demo2
```
SELECT SUM(AvgPrice) FROM Traded WHERE TradedDate=? AND Category=?;
```
> - Demo3
```
SELECT TradedDate,TransQuantity FROM Traded WHERE TradedDate BETWEEN ? AND ? AND ProductId =? AND MarketId=? ;
```
> - Demo4
```
SELECT ProductId, SUM(TransQuantity) AS tq FROM Traded WHERE TradedDate BETWEEN ? AND ? AND category=?  GROUP BY  ProductId  ORDER BY tq DESC  LIMIT 10;
```

# 使用技術
> ### 主要語言
>> - java JDK11
> ### 框架
>> - SpringBoot 2.62
> ### 建構工具
>> - Gradle
> ### 資料庫
>> - postgreSQL 14
> ### 資料庫存取技術
>> - spring boot data JDBC
> ### 前端
>> - Bootstrap5
>> - HTML5
# 使用方法
>> - 更改 src\main\resources\application.properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/databaseName //輸入databaseName
spring.datasource.username= //輸入userName  
spring.datasource.password= //輸入passWord
```
>> - 並執行程式,專案中schema.sql會自動建立好資料表 
>> - 程式進入點 http://localhost:8080/billhu/HomePage

# Restful API 文件
>> 網址 : http://localhost:8080/billhu/swagger-ui.html 




