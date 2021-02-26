package com.damoim.restapi.secondhandtrade.dao;

import static com.damoim.restapi.secondhandtrade.entity.useditem.QUsedItem.usedItem;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.model.SearchUsedItemRequest;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;


import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.internal.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UsedItemSearchRepository {

  private final JPAQueryFactory queryFactory;

  public Page<UsedItem> search(SearchUsedItemRequest request , Pageable pageable){
    QueryResults<UsedItem> results = queryFactory.selectFrom(usedItem)
        .where(
            likeTitle(request.getTitle()),
            likeDescription(request.getDescription()),
            eqWriter(request.getWriter()),
            eqPrice(request.getPrice()),
            likeAddress(request.getAddress()),
            eqTradeType(request.getTradeType()),
            eqCategory(request.getCategory()),
            eqEditWriter(request.getEditWriter()),
            eqClose(request.getClose()),
            eqNegotiation(request.getClose())
            )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    return new PageImpl<>(results.getResults(),pageable,results.getTotal());
  }

  private BooleanExpression likeTitle(String title){
    if(Strings.isEmpty(title)){
      return null;
    }
    return usedItem.title.contains(title);
  }

  private BooleanExpression likeDescription(String description){
    if(Strings.isEmpty(description)){
      return null;
    }
    return usedItem.description.contains(description);
  }

  private BooleanExpression eqWriter(String writer){
    if(Strings.isEmpty(writer)){
      return null;
    }
    return usedItem.writer.eq(writer);
  }

  private BooleanExpression eqPrice(Integer price){
    if(price ==null){
      return null;
    }
    return usedItem.price.eq(price);
  }

  private BooleanExpression likeAddress(String address){
    if(Strings.isEmpty(address)){
      return null;
    }
    return usedItem.address.contains(address);
  }

  private BooleanExpression eqTradeType(TradeType tradeType){
    if(tradeType== null){
      return null;
    }
    return usedItem.tradeType.eq(tradeType);
  }

  private BooleanExpression eqCategory(Category category){
    if(category == null){
      return null;
    }
    return usedItem.category.eq(category);
  }

  private BooleanExpression eqEditWriter(String editWriter){
    if(Strings.isEmpty(editWriter)){
      return null;
    }
    return usedItem.editWriter.eq(editWriter);
  }
  private BooleanExpression eqClose(Boolean result){
    if(result==null){
      return null;
    }
    return usedItem.close.eq(result);
  }

  private BooleanExpression eqNegotiation(Boolean result){
    if(result ==null){
      return null;
    }
    return usedItem.negotiation.eq(result);
  }
}