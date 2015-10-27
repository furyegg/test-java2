package ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class Test {

	public static void main(String[] args) throws NamingException {
		Hashtable evn = new Hashtable();
		evn.put(Context.SECURITY_PRINCIPAL, "cn=manager,dc=maxcrc,dc=com");
		evn.put(Context.SECURITY_CREDENTIALS, "secret");
		DirContext initial = new InitialDirContext(evn);
		DirContext context = (DirContext) initial.lookup("ldap://localhost:389");
		
		Attributes attributes = context.getAttributes("cn=manager,dc=maxcrc,dc=com");
		Attribute attribute = attributes.get("cn");
		String value = (String) attribute.get();
		System.out.println(value);
		
		NamingEnumeration<? extends Attribute> attrEnum = attributes.getAll();
		while (attrEnum.hasMore()) {
			Attribute attr = attrEnum.next();
			String id = attr.getID();
			System.out.println(id);
		}
	}

}