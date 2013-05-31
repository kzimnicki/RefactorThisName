bash "import sql" do
	code <<-EOH
		service iptables stop
		sudo chkconfig iptables off
		mysql -u root -p123456 -e "create database if not exists englishtranslator"
		mysql -u root -p123456 -e "create database if not exists testenglishtranslator"
	EOH
end
