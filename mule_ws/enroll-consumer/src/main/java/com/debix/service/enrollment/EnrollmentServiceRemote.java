package com.debix.service.enrollment;

import java.util.List;
import java.util.Map;

import com.debix.dto.AddressDTO;
import com.debix.dto.PaymentDTO;
import com.debix.dto.RegistrationDTO;
import com.debix.dto.types.AddressTypeDTO;
import com.debix.service.AlreadyRegisteredException;
import com.debix.service.PaymentRequiredException;
import com.debix.service.ValidationException;

public interface EnrollmentServiceRemote {

		public Map<String, Long> registerNew(List<RegistrationDTO> members, PaymentDTO payment, Map<AddressTypeDTO,AddressDTO> addressMap) 
				throws AlreadyRegisteredException, ValidationException, PaymentRequiredException;

}
