package io.pivotal.support.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SupportLdapDebugToolApplication {

	@Value("${spring.ldap.base}")
	private String baseDn;

	@Value("${ldap.filter}")
	private String filter;

	public static void main(String[] args) {
		SpringApplication.run(SupportLdapDebugToolApplication.class, args);
	}

	@Component
	public class Runner implements CommandLineRunner {

		@Autowired
		private LdapTemplate ldapTemplate;

		@Override
		public void run(String... args) throws Exception {
			System.out.println("Running LDAP search with filter -> " + filter);
			List<Map<String, List<String>>> result = ldapTemplate.search("", filter, new MapAttributesMapper());
			if (result.size() > 0) {
				int index = 0;
				for (Map<String, List<String>> item : result) {
					System.out.println("Record #" + ++index + ":");
					for (Map.Entry<String, List<String>> entry : item.entrySet()) {
						System.out.println("  " + entry.getKey() + ": " + entry.getValue().toString());
					}
					System.out.println();
				}
			} else {
				System.out.println("No results found :(");
			}
		}

	}

	public class MapAttributesMapper implements AttributesMapper<Map<String, List<String>>> {

		@Override
		public Map<String, List<String>> mapFromAttributes(Attributes attributes) throws NamingException {
			Map<String, List<String>> attrsMap = new HashMap<>();
			NamingEnumeration<String> attrIdEnum = attributes.getIDs();
			while (attrIdEnum.hasMoreElements()) {
				// Get attribute id:
				String attrId = attrIdEnum.next();
				// Get all attribute values:
				Attribute attr = attributes.get(attrId);
				NamingEnumeration<?> attrValuesEnum = attr.getAll();
				while (attrValuesEnum.hasMore()) {
					if (!attrsMap.containsKey(attrId))
						attrsMap.put(attrId, new ArrayList<String>());
					attrsMap.get(attrId).add(attrValuesEnum.next().toString());
				}
			}
			return attrsMap;
		}

	}
}
