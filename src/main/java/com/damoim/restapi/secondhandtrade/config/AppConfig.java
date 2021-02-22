package com.damoim.restapi.secondhandtrade.config;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.mapper.EnumMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public ModelMapper modelMapper(){
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
        .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
    return modelMapper;
  }

  @Bean
  public EnumMapper enumMapper(){
    EnumMapper enumMapper = new EnumMapper();
    //TODO 추후 key 값 하드코딩이 아닌 Enum 타입으로 따로 관리
    enumMapper.put("category", Category.class);
    enumMapper.put("tradeType",TradeType.class);
    return enumMapper;
  }
}
