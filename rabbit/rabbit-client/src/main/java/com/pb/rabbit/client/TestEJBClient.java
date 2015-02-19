package com.pb.rabbit.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.debix.dto.AddressDTO;
import com.debix.dto.RegistrationDTO;
import com.debix.dto.types.AddressTypeDTO;
import com.debix.service.AlreadyRegisteredException;
import com.debix.service.PaymentRequiredException;
import com.debix.service.ValidationException;
import com.debix.service.enrollment.EnrollmentServiceRemote;

//import org.jnp.interfaces.NamingContextFactory;

/**
 * Client to publish messages to an exchange on a remote rabbit mq broker. 
 */
public class TestEJBClient 
{
    Properties props;

    public static void main( String[] args )
    {

 
		Properties jndiProps = new Properties();
		jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		//jndiProps.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1099");
		jndiProps.put(Context.PROVIDER_URL,"jnp://172.23.1.238:1099");
		// create a context passing these properties
		Context ctx;
		EnrollmentServiceRemote client = null;
		try {
			ctx = new InitialContext(jndiProps);
			// lookup
			client = (EnrollmentServiceRemote) ctx.lookup("EnrollmentServiceBean/remote-com.debix.service.enrollment.EnrollmentServiceRemote");
			System.out.println("Obj = " + client);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			for (int i = 0; i < 1; i++) {
				RegistrationDTO dto = new RegistrationDTO();
				dto.setBirthdate(new Date(0L));
				dto.setCampaignLabel("premium005");
				dto.setEmail(RandomStringUtils.randomAlphanumeric(8) + "@" + RandomStringUtils.randomAlphanumeric(6) + "." + RandomStringUtils.randomAlphanumeric(3));
				dto.setFirstName(RandomStringUtils.randomAlphabetic(8));
				dto.setGender("F");
				dto.setIsMinor(false);
				dto.setIsPrimary(true);
				dto.setLastName(RandomStringUtils.randomAlphabetic(8));
				dto.setMiddleName(RandomStringUtils.randomAlphabetic(8));
				dto.setNameSuffix(null);
				dto.setPassword("password1");
				dto.setPhone("5123005972");
				dto.setPhoneLabel("Mobile");
				//dto.setSsn(RandomStringUtils.randomNumeric(9));
				dto.setOverrideRedemptionCode(false);
				dto.setOriginMethod("PHONE");
				dto.setServiceProvided(true);
				
				AddressDTO addressDto = new AddressDTO();
				addressDto.setAddressLine1(RandomStringUtils.randomAlphabetic(8));
				addressDto.setAddressLine2(RandomStringUtils.randomAlphabetic(8));
				addressDto.setAddressLine3(RandomStringUtils.randomAlphabetic(8));
				addressDto.setCity(RandomStringUtils.randomAlphabetic(8));
				addressDto.setStateProvince("AK");
				addressDto.setZipCode(RandomStringUtils.randomNumeric(5));
				
				Map<AddressTypeDTO,AddressDTO> addressMap = new HashMap<AddressTypeDTO, AddressDTO>();
				addressMap.put(AddressTypeDTO.PRIMARY_RESIDENCE, addressDto);
				
				List<RegistrationDTO> membersDTO = new ArrayList<RegistrationDTO>();
				membersDTO.add(dto);
				
				try {
					client.registerNew(membersDTO, null, addressMap);
				} catch (AlreadyRegisteredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PaymentRequiredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ValidationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}
    }
    
    
}
