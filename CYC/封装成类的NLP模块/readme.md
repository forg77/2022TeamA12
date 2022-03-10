可 from  SimCheck import  SimCheck 引入  
query1 = SimCheck()  
query1.Word2vec_similarity_check('test.txt','test2.txt',4) 依次为2个对比文件的路径 提取关键词数量  
query1.Bert_similarity_check('test.txt', 'test2.txt') 依次为2个对比文件的路径  
添加了搜索功能 可以用来检索题目/答案    
