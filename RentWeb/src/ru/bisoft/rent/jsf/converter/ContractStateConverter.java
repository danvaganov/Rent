package ru.bisoft.rent.jsf.converter;

import javax.faces.convert.EnumConverter;

import ru.bisoft.rent.model.Contract.ContractState;

public class ContractStateConverter extends EnumConverter {

	public ContractStateConverter() {
		super(ContractState.class);
	}

}
