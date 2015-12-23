
A：实验环境之组成
DNS：productserver
HA1（nameservices为cluster1）：product201、product202
HA2（nameservices为cluster2）：product211、product212
DN：product203、product204、product213、product214
JouralNode：product212、product213、product214
zookeeper：product202、product203、product204

B：实验环境之说明
product201、product202组成nameservices为cluster1的NN HA；
product211、product212组成nameservices为cluster2的NN HA；
product203、product204、product213、product214组成一个DataNode集群，提供HDFS存储；
product212、product213、product214组成的JournalNode集群，提供cluster1、cluster2各自active NN写入的EditLog共享目录，注意不同的nameservices要使用不同的共享目录。
product202、product203、product204组成zookeeper集群，提供cluster1、cluster2的NN HA的自动切换；

2：ssh无密码登陆配置
对本实验中的product201、product202、product203、product204、product211、product212、product213、product214进行ssh无密码登陆配置，具体做法参看 HDFS HA系列实验之经验总结  中6：关于ssh免密码的操作步骤。

3：hadoop集群配置
A：修改HDFS HA系列实验之二：HA+JournalNode+zookeeper  中的配置文件hdfs-site.xml文件如下，并发放给所有节点。