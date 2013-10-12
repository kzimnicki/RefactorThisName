bash "import sql" do
	code <<-EOH
		service iptables stop
		sudo chkconfig iptables off
		mysql -u root -p123456 -e "create database if not exists testenglishtranslator"
		mysql -u root -p123456 -e "create database if not exists englishtranslator"
		mysql -u root -p123456 --force --default-character-set=utf8 testenglishtranslator < /vagrant/sql/ddl_explain_cc_database.sql
		mysql -u root -p123456 --force --default-character-set=utf8 englishtranslator < /vagrant/sql/ddl_explain_cc_database.sql
		mysql -u root -p123456 --default-character-set=utf8 testenglishtranslator < /vagrant/sql/import.sql
		mysql -u root -p123456 --default-character-set=utf8 englishtranslator < /vagrant/sql/import.sql
		mysql -u root -p123456 --default-character-set=utf8 testenglishtranslator < /vagrant/sql/update_20130623/changePass.sql
        mysql -u root -p123456 --default-character-set=utf8 englishtranslator < /vagrant/sql/update_20130623/changePass.sql
	EOH
end
