1.简介
ReentrantLock即为重入锁，重入是指同一线程可以多次进入该锁，外在表现和synchronized相同。用于线程同步。
2.与synchronized区别
首先区别在于ReentrantLock在阻塞在getLock的过程中可以进行interrupt取消申请锁，并且可以指定等待时间，等待时间内拿不到锁即放弃申请锁，并可以直接用无参的tryLock无等待尝试锁。
ReentrantLock的开发即是用来扩展synchronized关键字，不过经过jdk1.6的优化性能已经没有什么区别了
3.相关的操作
lockInterruptly()，可以被中断的锁
lock()纯粹的锁
tryLock(time, timeUnit)等待time时间，超时放弃申请锁
tryLock()不等待直接尝试枷锁，不存在超时


4.源码阅读
首先ReentrantLock中有 一个Sync抽象类，并内含了FairSync和UnfairSync两个实现，分别实现公平锁和非公平锁。