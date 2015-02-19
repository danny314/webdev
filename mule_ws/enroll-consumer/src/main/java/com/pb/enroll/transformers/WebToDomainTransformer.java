package com.pb.enroll.transformers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

import com.debix.dto.AddressDTO;
import com.debix.dto.RegistrationDTO;
import com.debix.dto.types.AddressTypeDTO;
import com.debix.enrollment.beans.RegistrationView;
import com.debix.service.AlreadyRegisteredException;
import com.debix.service.PaymentRequiredException;
import com.debix.service.ValidationException;
import com.debix.service.enrollment.EnrollmentServiceRemote;

public class WebToDomainTransformer extends AbstractTransformer {
	
	public WebToDomainTransformer() {
        super();
        this.registerSourceType(DataTypeFactory.create(com.debix.enrollment.beans.RegistrationView.class));
        this.setReturnDataType(DataTypeFactory.create(java.util.Map.class));
    }

	@Override
	protected Object doTransform(Object src, String enc) throws TransformerException {
		
		String remoteJndiHost = muleContext.getRegistry().get("remote.jndi.host");
		String remoteJndiPort = muleContext.getRegistry().get("remote.jndi.port");
		String remoteServiceInterface = muleContext.getRegistry().get("remote.service.interface");

		
		RegistrationView regView = (RegistrationView) src;
		
		Properties jndiProps = new Properties();
		jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		
		jndiProps.put(Context.PROVIDER_URL,"jnp://"+remoteJndiHost+":"+remoteJndiPort);
		
		Context ctx;
		EnrollmentServiceRemote client = null;
		try {
			ctx = new InitialContext(jndiProps);
			client = (EnrollmentServiceRemote) ctx.lookup(remoteServiceInterface);
		} catch (NamingException e) {
			e.printStackTrace();
		}
			
				RegistrationDTO dto = new RegistrationDTO();
				dto.setBirthdate(new Date(0L));
				dto.setCampaignLabel("free001");
				dto.setEmail(RandomStringUtils.randomAlphanumeric(8) + "@" + RandomStringUtils.randomAlphanumeric(6) + "." + RandomStringUtils.randomAlphanumeric(3));
				dto.setFirstName(regView.getName());
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
				
				Map<String,Long> registeredMembers = null;
				try {
					registeredMembers = client.registerNew(membersDTO, null, addressMap);
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
				
			return registeredMembers;
	}
}
