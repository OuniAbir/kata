package sg.banking.kata.katasgbanking.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.entities.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionDTO toDto(Transaction transaction);

    @Mapping(target = "account", ignore = true) // Ignoring the 'account' field
    Transaction toEntity(TransactionDTO transactionDTO);
}