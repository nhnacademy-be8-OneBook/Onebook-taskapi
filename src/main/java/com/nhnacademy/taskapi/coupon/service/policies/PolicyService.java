package com.nhnacademy.taskapi.coupon.service.policies;

import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.exception.PolicyStatusNotFoundException;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.status.PolicyStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final PolicyStatusRepository policyStatusRepository;

    private final RatePoliciesForBookRepository ratePoliciesForBookRepository;
    private final RatePoliciesForCategoryRepository ratePoliciesForCategoryRepository;

    // 정률정책 for Book save
    public AddRatePolicyForBookResponse addRatePolicyForBook(AddRatePolicyForBookRequest addRatePolicyForBookRequest){

        RatePolicyForBook ratePolicyForBook = RatePolicyForBook.createRatePolicyForBook(
                addRatePolicyForBookRequest,
                bookRepository.findById(addRatePolicyForBookRequest.getBookId())
                        .orElseThrow(()->new BookNotFoundException("해당하는 ID의 도서를 찾을 수 없습니다")),
                policyStatusRepository.findById(addRatePolicyForBookRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을수 없습니다"))
                );

        ratePoliciesForBookRepository.save(ratePolicyForBook);
        return AddRatePolicyForBookResponse.changeEntityToDto(ratePolicyForBook);

    }

    // 정률정책 for Category save
    public AddRatePolicyForCategoryResponse addRatePolicyForCategory(AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest){

        RatePolicyForCategory ratePolicyForCategory = RatePolicyForCategory.createRatePolicyForCategory(
                addRatePolicyForCategoryRequest,
                categoryRepository.findById(addRatePolicyForCategoryRequest.getCategoryId())
                        .orElseThrow(()->new CategoryNotFoundException("해당하는 ID의 카테고리를 찾을 수 없습니다")),
                policyStatusRepository.findById(addRatePolicyForCategoryRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을수 없습니다"))
                );

        ratePoliciesForCategoryRepository.save(ratePolicyForCategory);
        return AddRatePolicyForCategoryResponse.changeEntityToDto(ratePolicyForCategory);
    }
}
