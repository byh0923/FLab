package com.flab;

import java.util.List;
import java.util.function.Function;

public class Prototype {
    public static void main(String[] args) {

        /** 
         * 임시 객체
         * */
        Member member = new Member();
        Item item = new Item();
        Seller seller = new Seller();
        Buyer buyer = new Buyer();
        ItemManager itemManager = new ItemManager();
        MemberManager memberManager = new MemberManager();
        ZzimManager zzimManager = new ZzimManager();
        TransactionManager transactionManager = new TransactionManager();

        /**
         * 기본기능
         * */
        // 회원가입
        memberManager.registerMember(member);

        /**
         * 판매자
         * */
        itemManager.registerItem(item, member.getMemberNo());

        //추천 리스트 보여주기
        List<Item> recommendList = itemManager.showRecommendItemList(member);

        // 상품 리스트 보여주기
        List<Item> normalList = itemManager.showItemList(item);

        /**
         * 회원인지 아닌지 체크 후 회원이 아니면 로그인 메소드를 타도록 함.
         * */
        if(!memberManager.isRegistered(member)) {
            memberManager.goLogin();
        }


        /**
         * 구매자
         * */
        // 상품 선택
        Item selectItem = itemManager.getItem(item.getItemNo());

        // 찜하기
        zzimManager.zzim(selectItem.getItemNo(), member.getMemberNo());

        // 연락하기
        buyer.sendMessage(selectItem.getItemNo());

        /**
         * 구매자가 거래방법 체크하는 메소드 추가
         * enum 클래스 활용하여 사용자가 직거래나 택배거래를 선택하면 해당 코드 반환
         * */
        int selectDealTypeCode = DealType.courierServiceBuy.getDealTypeCode();

        /**
         * isDirectTransaction()을 통해 택배배송인지 직거래인지 체크해서 true 직거래 그렇지 않으면 택배거래를 하도록 함
         * */
        boolean checkIsDirectTransaction = transactionManager.isDirectTransaction(selectDealTypeCode);

        // if문 제거 람다식으로 변환
        Function<Boolean, Boolean> isDirectTransaction = check -> true ? transactionManager.directTransaction(selectItem.getItemNo(), member.getMemberNo())
                : transactionManager.courierServiceBuy(selectItem.getItemNo(), member.getMemberNo());
        isDirectTransaction.apply(checkIsDirectTransaction);

    }
}


