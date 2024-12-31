package com.nhnacademy.taskapi.coupon.service.policies;

import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddPricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetPricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetPricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddPricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.exception.PolicyNotFoundException;
import com.nhnacademy.taskapi.coupon.exception.PolicyStatusNotFoundException;
import com.nhnacademy.taskapi.coupon.repository.policies.PricePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.PricePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.status.PolicyStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final PolicyStatusRepository policyStatusRepository;

    private final RatePoliciesForBookRepository ratePoliciesForBookRepository;
    private final RatePoliciesForCategoryRepository ratePoliciesForCategoryRepository;
    private final PricePoliciesForBookRepository pricePoliciesForBookRepository;
    private final PricePoliciesForCategoryRepository pricePoliciesForCategoryRepository;

    private final Integer PAGE_SIZE = 10;

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

    // 정액정책 for Book save
    public AddPricePolicyForBookResponse addPricePolicyForBook(AddPricePolicyForBookRequest addPricePolicyForBookRequest){

        PricePolicyForBook pricePolicyForBook = PricePolicyForBook.createPricePolicyForBook(
                addPricePolicyForBookRequest,
                bookRepository.findById(addPricePolicyForBookRequest.getBookId())
                        .orElseThrow(()->new BookNotFoundException("해당하는 ID의 도서를 찾을 수 없습니다")),
                policyStatusRepository.findById(addPricePolicyForBookRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을수 없습니다"))
        );

                pricePoliciesForBookRepository.save(pricePolicyForBook);
                return AddPricePolicyForBookResponse.changeEntityToDto(pricePolicyForBook);
    }

    // 정액정책 for Category save
    public AddPricePolicyForCategoryResponse addPricePolicyForCategory(AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest){

        PricePolicyForCategory pricePolicyForCategory = PricePolicyForCategory.createPricePolicyForCategory(
                addPricePolicyForCategoryRequest,
                categoryRepository.findById(addPricePolicyForCategoryRequest.getCategoryId())
                        .orElseThrow(()->new CategoryNotFoundException("해당하는 ID의 카테고리를 찾을 수 없습니다")),
                policyStatusRepository.findById(addPricePolicyForCategoryRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을 수 없습니다"))
                );

                pricePoliciesForCategoryRepository.save(pricePolicyForCategory);
                return AddPricePolicyForCategoryResponse.changeEntityToDto(pricePolicyForCategory);
    }

    // 정률정책 for Book read (all)
    public List<GetRatePolicyForBookResponse> getRatePoliciesForBook(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<RatePolicyForBook> page = ratePoliciesForBookRepository.findAll(pageable);

        List<GetRatePolicyForBookResponse> result = new ArrayList<>();

        for(RatePolicyForBook ratePolicyForBook : page){
            result.add(GetRatePolicyForBookResponse.changeEntityToDto(ratePolicyForBook));
        }

        return result;
    }

    // 정률정책 for Category read (all)
    public List<GetRatePolicyForCategoryResponse> getRatePoliciesForCategory(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<RatePolicyForCategory> page = ratePoliciesForCategoryRepository.findAll(pageable);

        List<GetRatePolicyForCategoryResponse> result = new ArrayList<>();

        for(RatePolicyForCategory ratePolicyForCategory : page){
            result.add(GetRatePolicyForCategoryResponse.changeEntityToDto(ratePolicyForCategory));
        }

        return result;
    }

    // 정액정책 for Book read (all)
    public List<GetPricePolicyForBookResponse> getPricePoliciesForBook(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<PricePolicyForBook> page = pricePoliciesForBookRepository.findAll(pageable);

        List<GetPricePolicyForBookResponse> result = new ArrayList<>();

        for(PricePolicyForBook pricePolicyForBook : page){
            result.add(GetPricePolicyForBookResponse.changeEntityToDto(pricePolicyForBook));
        }

        return result;
    }

    // 정액정책 for Category read (all)
    public List<GetPricePolicyForCategoryResponse> getPricePoliciesForCategory(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<PricePolicyForCategory> page = pricePoliciesForCategoryRepository.findAll(pageable);

        List<GetPricePolicyForCategoryResponse> result = new ArrayList<>();

        for(PricePolicyForCategory pricePolicyForCategory : page){
            result.add(GetPricePolicyForCategoryResponse.changeEntityToDto(pricePolicyForCategory));
        }

        return result;
    }

    // 정률정책 for Book read (one)
    public GetRatePolicyForBookResponse getRatePolicyForBook(Long id){

        RatePolicyForBook ratePolicyForBook = ratePoliciesForBookRepository.findById(id)
                .orElseThrow(() ->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return GetRatePolicyForBookResponse.changeEntityToDto(ratePolicyForBook);
    }

    // 정률정책 for Category read (one)
    public GetRatePolicyForCategoryResponse getRatePolicyForCategory(Long id){

        RatePolicyForCategory ratePolicyForCategory = ratePoliciesForCategoryRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return GetRatePolicyForCategoryResponse.changeEntityToDto(ratePolicyForCategory);
    }

    // 정액정책 for Book read (one)
    public GetPricePolicyForBookResponse getPricePolicyForBook(Long id){

        PricePolicyForBook pricePolicyForBook = pricePoliciesForBookRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return GetPricePolicyForBookResponse.changeEntityToDto(pricePolicyForBook);
    }

    // 정액정책 for Category read (one)
    public GetPricePolicyForCategoryResponse getPricePolicyForCategory(Long id){

        PricePolicyForCategory pricePolicyForCategory = pricePoliciesForCategoryRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return GetPricePolicyForCategoryResponse.changeEntityToDto(pricePolicyForCategory);
    }

}
