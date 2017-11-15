###简介

爬取并筛选出可以生成的代码代码片段并利用代码片段中的 Type 和 Method 调用信息在 github 上查找类似的代码从而估计生成效果。

### 使用方法

首先需要在修改配置文件 GetRelativeCodes/configure.conf，填写上 github 的账号和密码。

1. 爬取 Query。

   1. 在 GetData 目录下运行 ```python3 main.py start_page project_number```，其中 start_page 表示第几页的仓库开始，project_number 表示爬取多少项目。
   2. 结果会存储在项目目录下的 dataset 文件夹中。

2. 从特定的一些代码中查找 Query。

   1. 将代码存储在 GetData/Query 中并命名成 ```number.java``` 的形式。
   2. 在 GetData 目录下运行 ```java -jar QueryMethodFinder.jar l r```，其中 l, r 表示检查 GetData/Query 中 ```l.java``` 到 ``` r.java``` 的代码。
   3. 结果会存储在项目目录下的 dataset 文件夹中。

3. 查找一些特定的 Query 的相关代码。

   1. 将询问保存在 GetData/data 中并命名成 ```number.java``` 的形式。

   2. 每一个询问保存三行，每一行都形如 ```// describe_string``` 的形式，其中第一行描述用到的 method，第二行描述用到的 type，第三行描述询问来源的仓库名。其中前两行多个对象之间都用加号隔开，第三行仓库名可以为空。例如：

      ```
      // parseInt+toCharArray+digit
      // Integer+String+Character
      // DeepAPILearning
      ```

   3. 结果会存储在项目目录下的 dataset 文件夹中。

   ​

    

