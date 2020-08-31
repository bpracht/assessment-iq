create table tb_batch(
    batch_id integer,
    requestor varchar(100) character set utf8,
    request_date_time timestamp,
    request_status varchar(100),
    request_for_system varchar(100)
);


create table tb_batch_item(
    batch_id integer,
    item integer,
    name varchar(100) character set utf8,
    email varchar(100),
    init_password varchar(100),
    role varchar(100),
    reason_for_access varchar(255) character set utf8
);

insert into tb_batch(batch_id,requestor,request_date_time,request_status,request_for_system)
values(518,'bmore',timestamp('2013-10-27 12:34:23'),'Queue','ClinOp');

insert into tb_batch_item(Batch_Id, Item, Name, Email, Init_Password, Role, Reason_For_Access)
values(518,1,'Susan Smith','ssmith@comany1.com','susan12%#?','SuperUser','Because I am \"cool\", I can do whatever I want.');

insert into tb_batch_item(Batch_Id, Item, Name, Email, Init_Password, Role, Reason_For_Access)
values(518,2,'Alex O\'Connor','alexoconnor@univ1.edu','itsuniv1','ReadOnly','I need to access report for budget < 1M $');

insert into tb_batch_item(Batch_Id, Item, Name, Email, Init_Password, Role, Reason_For_Access)
values(518,3,'John J. Peterson','john.p@comany2.com','J.Pe1234!','Auditor','Access to 1) all reports; 2) server system logs for \"Audit\" and [app]_Access_Log');

insert into tb_batch_item(Batch_Id, Item, Name, Email, Init_Password, Role, Reason_For_Access)
values(518,4,'Chen, Mei 陈梅','chehmei12@123.com','<:-)>{;=0}','ReadOnly','我负责中国分公司财务');

