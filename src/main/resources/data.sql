insert into grp values ('admin','Super Admin');
insert into grp values ('user','User');
insert into grp values ('organisasi','Organisasi');

insert into menu (id,name,url,header) values ('root','','',null);
insert into menu (id,name,url,header) values ('akses','Admin','akses','root');
insert into menu (id,name,url,header) values ('peminjam','Daftar Peminjam','peminjam','akses');
insert into menu (id,name,url,header) values ('daftarakses','Daftar Hak Akses','daftarakses','akses');
insert into menu (id,name,url,header) values ('akun','Profil','akun','root');
insert into menu (id,name,url,header) values ('editakun','Edit Profil','editakun','akun');
insert into menu (id,name,url,header) values ('buku','Perpustakaan','buku','root');
insert into menu (id,name,url,header) values ('daftar','Daftar Buku','daftar','buku');
insert into menu (id,name,url,header) values ('pinjaman','Pinjaman','pinjaman','buku');
/*insert into menu (id,name,url,header) values ('konfirmasi','Konfirmasi','konfirmasi','root');*/

insert into menuacs (acsid,menuid,enb) values ('ENB' ,'akses'      ,true);
insert into menuacs (acsid,menuid,enb) values ('ENB' ,'peminjam'   ,true);
insert into menuacs (acsid,menuid,enb) values ('ENB' ,'daftarakses',true);
insert into menuacs (acsid,menuid,enb) values ('NEW' ,'daftarakses',true);
insert into menuacs (acsid,menuid,enb) values ('EDIT','daftarakses',true);
insert into menuacs (acsid,menuid,enb) values ('VOID','daftarakses',true);
insert into menuacs (acsid,menuid,enb) values ('ENB' ,'akun'       ,true);
insert into menuacs (acsid,menuid,enb) values ('ENB' ,'editakun'   ,true);
insert into menuacs (acsid,menuid,enb) values ('EDIT','editakun'   ,true);
insert into menuacs (acsid,menuid,enb) values ('ENB' ,'buku'       ,true);
insert into menuacs (acsid,menuid,enb) values ('ENB' ,'daftar'     ,true);
insert into menuacs (acsid,menuid,enb) values ('NEW' ,'daftar'     ,true);
insert into menuacs (acsid,menuid,enb) values ('EDIT','daftar'     ,true);
insert into menuacs (acsid,menuid,enb) values ('VOID','daftar'     ,true);
insert into menuacs (acsid,menuid,enb) values ('ENB' ,'pinjaman'   ,true);
insert into menuacs (acsid,menuid,enb) values ('NEW' ,'pinjaman'   ,true);
insert into menuacs (acsid,menuid,enb) values ('EDIT','pinjaman'   ,true);
insert into menuacs (acsid,menuid,enb) values ('VOID','pinjaman'   ,true);
/*insert into menuacs (acsid,menuid,enb) values ('ENB' ,'konfirmasi' ,true);
insert into menuacs (acsid,menuid,enb) values ('EDIT','konfirmasi' ,true);*/

insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','akses'      ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','peminjam'   ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','daftarakses',true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'admin','daftarakses',true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','admin','daftarakses',true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','admin','daftarakses',true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','akun'       ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','editakun'   ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','admin','editakun'   ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','buku'       ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','daftar'     ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'admin','daftar'     ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','admin','daftar'     ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','admin','daftar'     ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','pinjaman'   ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'admin','pinjaman'   ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','admin','pinjaman'   ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','admin','pinjaman'   ,true);
/*insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','konfirmasi' ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','admin','konfirmasi' ,true);*/


insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','akses'      ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','peminjam'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'user','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','user','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','user','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','akun'       ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','editakun'   ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','user','editakun'   ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','buku'       ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','daftar'     ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'user','daftar'     ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','user','daftar'     ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','user','daftar'     ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'user','pinjaman'   ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'user','pinjaman'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','user','pinjaman'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','user','pinjaman'   ,false);
/*insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','konfirmasi' ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','admin','konfirmasi' ,true);*/

insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','akses'      ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','peminjam'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'organisasi','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','organisasi','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','organisasi','daftarakses',false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','akun'       ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','editakun'   ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','organisasi','editakun'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','buku'       ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','daftar'     ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'organisasi','daftar'     ,true );
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','organisasi','daftar'     ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','organisasi','daftar'     ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'organisasi','pinjaman'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('NEW' ,'organisasi','pinjaman'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','organisasi','pinjaman'   ,false);
insert into grpaprv (acsid,grpid,menuid,enb) values ('VOID','organisasi','pinjaman'   ,false);
/*insert into grpaprv (acsid,grpid,menuid,enb) values ('ENB' ,'admin','konfirmasi' ,true);
insert into grpaprv (acsid,grpid,menuid,enb) values ('EDIT','admin','konfirmasi' ,true);*/
