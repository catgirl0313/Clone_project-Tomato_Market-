package com.hanghaecloneproject.trade.repository;

import com.hanghaecloneproject.trade.domain.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TradeReadRepositoryImpl implements TradeReadRepositoryCustom {

    @Override
    public Page<Trade> findByIdLessThanAndTitleContainingAndAddressContaining(Long id, String title,
          String address, Pageable pageable) {
        return null;
    }
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    public TradeReadRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
//        this.jpaQueryFactory = jpaQueryFactory;
//    }
//
//    @Override
//    public Page<Trade> findByIdLessThanAndTitleContainingAndAddressContaining(Long id, String title,
//          String address, Pageable pageable) {
//        List<Trade> content = jpaQueryFactory.selectFrom(trade)
//              .where(
//                    idGoe(id),
//                    titleContaining(title),
//                    addressContaining(address)
//              )
//              .offset(pageable.getOffset())
//              .limit(pageable.getPageSize())
//              .fetch();
//
//        Long count = jpaQueryFactory
//              .select(trade.count())
//              .from(trade)
//              .where(
//                    idGoe(id),
//                    titleContaining(title),
//                    addressContaining(address)
//              )
//              .offset(pageable.getOffset())
//              .limit(pageable.getPageSize())
//              .fetchOne();
//
//        return new PageImpl<>(content, pageable, count);
//    }
//
//    private Predicate idGoe(Long id) {
//        return id != null ? trade.id.goe(id) : null;
//    }
//
//    private Predicate titleContaining(String title) {
//        return title != null ? trade.title.contains(title) : null;
//    }
//
//    private Predicate addressContaining(String address) {
//        return address != null ? trade.address.contains(address) : null;
//    }
}
