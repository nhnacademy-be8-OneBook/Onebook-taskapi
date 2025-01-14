package com.nhnacademy.taskapi.coupon.repository.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.coupons.IssuedCoupon;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.QIssuedCoupon;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import com.nhnacademy.taskapi.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class CouponBoxRepositoryImpl implements  CouponBoxRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<IssuedCoupon> findByMemberAndCoupon_CouponStatus_Name(Member member, String couponCouponStatusName, Pageable pageable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends IssuedCoupon> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends IssuedCoupon> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<IssuedCoupon> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public IssuedCoupon getOne(Long aLong) {
        return null;
    }

    @Override
    public IssuedCoupon getById(Long aLong) {
        return null;
    }

    @Override
    public IssuedCoupon getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends IssuedCoupon> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends IssuedCoupon> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends IssuedCoupon> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends IssuedCoupon> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends IssuedCoupon> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends IssuedCoupon> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends IssuedCoupon, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends IssuedCoupon> S save(S entity) {
        return null;
    }

    @Override
    public <S extends IssuedCoupon> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<IssuedCoupon> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<IssuedCoupon> findAll() {
        return List.of();
    }

    @Override
    public List<IssuedCoupon> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(IssuedCoupon entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends IssuedCoupon> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<IssuedCoupon> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<IssuedCoupon> findAll(Pageable pageable) {
        return null;
    }

    public Page<IssuedCoupon> getValidIssuedCoupon(Long memberId, CouponStatus couponStatus, Pageable pageable){

        QIssuedCoupon issuedCoupon = QIssuedCoupon.issuedCoupon;

        List<IssuedCoupon> content = jpaQueryFactory
                .selectFrom(issuedCoupon)
                .where(
                        issuedCoupon.member.id.eq(memberId),
                        issuedCoupon.useDateTime.isNull(),
                        issuedCoupon.coupon.couponStatus.ne(couponStatus)
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(issuedCoupon)
                .where(
                        issuedCoupon.member.id.eq(memberId),
                        issuedCoupon.useDateTime.isNull(),
                        issuedCoupon.coupon.couponStatus.ne(couponStatus)
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .stream().count();

        return new PageImpl<>(content,pageable,total);
    }
}
