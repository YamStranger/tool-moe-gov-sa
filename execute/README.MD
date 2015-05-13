Here is solution for downloading schools from search https://www.moe.gov.sa/English/EServices/Public/Pages/SchoolByEducationalOffice.aspx
This solution contains 3 different programs
-	Tool for downloading data from site
-	Tool for filtering schools 
All of them will be described below. 
System requirements:
-	java 8 need to be installed on your PC. To check this you can ran “java –version” on your command prompt. 
-	You need internet access to this site
-	Chrome browser must be installed on your pc
In my instructions I will use key-word “java_path” – this is path to your java installation (for me it is C:\Java\jdk8.5\bin\java.exe)
-------------------------------------------------------------
Tool for downloading data from site. 
To start this program you need open command prompt in directory “execute”, with same structure as in archive. So just open command prompt and navigate to directory with this solution. 
To start program execution, please run command:
	java_path -jar tool-moe-gov-sa-all-1.0.jar -mod=1 -rest=0 -threads=2 >out.lst
Please be carefully with structure of this directory. During execution of this program all debug info will be in out.lst file. All results will be in www.moe.gov.sa.csv file, and result markers will be in storage.bin file. 
www.moe.gov.sa.csv – contains spreadsheet with all fields of school (name, mail, etc)
storage.bin – contains internal data in human friendly format. Here you can find search criteria, which are successfully processed (and result is in www.moe.gov.sa.csv file).  Last time there was 344 search criteria on site, so you can count how many % already processed. 
Execution of program has three stage
1)	Read from site search criteria. On this stage you will see only one open window.
2)	Choose new creation, which does not present in storage.bin. (because all criteria in this file are “successfully processed”) – you will see no open browser
3)	Download all schools under this search criterion. Here you can see near 5 browsers per criterion. 
Two search criterion are processed in same time (in normal window, and in “incognito window”).
When program finish 3 stage – if will write results to www.moe.gov.sa.csv file, and write this search criterion to storage.bin – to skip in future. 

If you want continue last search – please, be sure that file storage.bin contains results of that search, otherwise it will start from first criteria. 
If you want start search again – just remove storage.bin, or rename it. 


Filtering data
During search, some schools can be duplicated. To avoid this – you can filter results, and prepare file without duplicates. 
For this, you need copy files (*.csv) with this data(different versions of www.moe.gov.sa.csv file) in “results” directory, in directory “execute”. After that in directory execute you need start program Filter-1.0.jar
You need to run in command prompt in “execute” directory next command:
java_path -jar Filter-1.0.jar
Program will process all *.csv files in results directory, and remove duplicates. Results will be in file “result.csv”
