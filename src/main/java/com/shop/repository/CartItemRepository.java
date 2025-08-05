package com.shop.repository;

import com.shop.dto.CartDetailDto;
import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 1 장바구니 ID와 아이템 ID로 장바구니 항목을 찾는 메서드
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    /**
     -- 장바구니 ID가 1인 항목의 상품명, 가격, 수량, 대표 이미지 경로를 최신순으로 조회
     SELECT ci.cart_id, ci.cart_item_id, i.item_nm, i.price, ci.count, im.img_url
     FROM cart_item ci
     JOIN item i ON ci.item_id = i.item_id
     JOIN item_img im ON im.item_id = ci.item_id
     WHERE ci.cart_id = 1 AND im.repimg_yn = 'Y'
     ORDER BY ci.reg_time DESC;
     */


    // JPQL 쿼리를 사용하여 장바구니 상세 정보를 조회하는 메서드 선언
    @Query("select new com.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.regTime desc"
    )


    // 장바구니 상세 정보를 리스트로 반환하는 메서드 선언
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
