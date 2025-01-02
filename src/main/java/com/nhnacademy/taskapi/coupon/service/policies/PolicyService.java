package com.nhnacademy.taskapi.coupon.service.policies;

import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.PricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.PricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.RatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.RatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.PolicyStatus;
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
    public RatePolicyForBookResponse addRatePolicyForBook(AddRatePolicyForBookRequest addRatePolicyForBookRequest){

        RatePolicyForBook ratePolicyForBook = RatePolicyForBook.createRatePolicyForBook(
                addRatePolicyForBookRequest,
                bookRepository.findById(addRatePolicyForBookRequest.getBookId())
                        .orElseThrow(()->new BookNotFoundException("해당하는 ID의 도서를 찾을 수 없습니다")),
                policyStatusRepository.findById(addRatePolicyForBookRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을수 없습니다"))
                );

                ratePoliciesForBookRepository.save(ratePolicyForBook);
                return RatePolicyForBookResponse.changeEntityToDto(ratePolicyForBook);
    }

    // 정률정책 for Category save
    public RatePolicyForCategoryResponse addRatePolicyForCategory(AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest){

        RatePolicyForCategory ratePolicyForCategory = RatePolicyForCategory.createRatePolicyForCategory(
                addRatePolicyForCategoryRequest,
                categoryRepository.findById(addRatePolicyForCategoryRequest.getCategoryId())
                        .orElseThrow(()->new CategoryNotFoundException("해당하는 ID의 카테고리를 찾을 수 없습니다")),
                policyStatusRepository.findById(addRatePolicyForCategoryRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을수 없습니다"))
                );

                ratePoliciesForCategoryRepository.save(ratePolicyForCategory);
                return RatePolicyForCategoryResponse.changeEntityToDto(ratePolicyForCategory);
    }

    // 정액정책 for Book save
    public PricePolicyForBookResponse addPricePolicyForBook(AddPricePolicyForBookRequest addPricePolicyForBookRequest){

        PricePolicyForBook pricePolicyForBook = PricePolicyForBook.createPricePolicyForBook(
                addPricePolicyForBookRequest,
                bookRepository.findById(addPricePolicyForBookRequest.getBookId())
                        .orElseThrow(()->new BookNotFoundException("해당하는 ID의 도서를 찾을 수 없습니다")),
                policyStatusRepository.findById(addPricePolicyForBookRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을수 없습니다"))
        );

                pricePoliciesForBookRepository.save(pricePolicyForBook);
                return PricePolicyForBookResponse.changeEntityToDto(pricePolicyForBook);
    }

    // 정액정책 for Category save
    public PricePolicyForCategoryResponse addPricePolicyForCategory(AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest){

        PricePolicyForCategory pricePolicyForCategory = PricePolicyForCategory.createPricePolicyForCategory(
                addPricePolicyForCategoryRequest,
                categoryRepository.findById(addPricePolicyForCategoryRequest.getCategoryId())
                        .orElseThrow(()->new CategoryNotFoundException("해당하는 ID의 카테고리를 찾을 수 없습니다")),
                policyStatusRepository.findById(addPricePolicyForCategoryRequest.getPolicyStatusId())
                        .orElseThrow(()->new PolicyStatusNotFoundException("해당하는 ID의 정책상태를 찾을 수 없습니다"))
                );

                pricePoliciesForCategoryRepository.save(pricePolicyForCategory);
                return PricePolicyForCategoryResponse.changeEntityToDto(pricePolicyForCategory);
    }

    // 정률정책 for Book read (all)
    public List<RatePolicyForBookResponse> getRatePoliciesForBook(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<RatePolicyForBook> page = ratePoliciesForBookRepository.findAll(pageable);

        List<RatePolicyForBookResponse> result = new ArrayList<>();

        for(RatePolicyForBook ratePolicyForBook : page){
            result.add(RatePolicyForBookResponse.changeEntityToDto(ratePolicyForBook));
        }

        return result;
    }

    // 정률정책 for Category read (all)
    public List<RatePolicyForCategoryResponse> getRatePoliciesForCategory(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<RatePolicyForCategory> page = ratePoliciesForCategoryRepository.findAll(pageable);

        List<RatePolicyForCategoryResponse> result = new ArrayList<>();

        for(RatePolicyForCategory ratePolicyForCategory : page){
            result.add(RatePolicyForCategoryResponse.changeEntityToDto(ratePolicyForCategory));
        }

        return result;
    }

    // 정액정책 for Book read (all)
    public List<PricePolicyForBookResponse> getPricePoliciesForBook(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<PricePolicyForBook> page = pricePoliciesForBookRepository.findAll(pageable);

        List<PricePolicyForBookResponse> result = new ArrayList<>();

        for(PricePolicyForBook pricePolicyForBook : page){
            result.add(PricePolicyForBookResponse.changeEntityToDto(pricePolicyForBook));
        }

        return result;
    }

    // 정액정책 for Category read (all)
    public List<PricePolicyForCategoryResponse> getPricePoliciesForCategory(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<PricePolicyForCategory> page = pricePoliciesForCategoryRepository.findAll(pageable);

        List<PricePolicyForCategoryResponse> result = new ArrayList<>();

        for(PricePolicyForCategory pricePolicyForCategory : page){
            result.add(PricePolicyForCategoryResponse.changeEntityToDto(pricePolicyForCategory));
        }

        return result;
    }

    // 정률정책 for Book read (one)
    public RatePolicyForBookResponse getRatePolicyForBook(Long id){

        RatePolicyForBook ratePolicyForBook = ratePoliciesForBookRepository.findById(id)
                .orElseThrow(() ->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return RatePolicyForBookResponse.changeEntityToDto(ratePolicyForBook);
    }

    // 정률정책 for Category read (one)
    public RatePolicyForCategoryResponse getRatePolicyForCategory(Long id){

        RatePolicyForCategory ratePolicyForCategory = ratePoliciesForCategoryRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return RatePolicyForCategoryResponse.changeEntityToDto(ratePolicyForCategory);
    }

    // 정액정책 for Book read (one)
    public PricePolicyForBookResponse getPricePolicyForBook(Long id){

        PricePolicyForBook pricePolicyForBook = pricePoliciesForBookRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return PricePolicyForBookResponse.changeEntityToDto(pricePolicyForBook);
    }

    // 정액정책 for Category read (one)
    public PricePolicyForCategoryResponse getPricePolicyForCategory(Long id){

        PricePolicyForCategory pricePolicyForCategory = pricePoliciesForCategoryRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        return PricePolicyForCategoryResponse.changeEntityToDto(pricePolicyForCategory);
    }

    // 정률정책 for Book delete
    public RatePolicyForBookResponse deleteRatePolicyForBook(Long id){

        RatePolicyForBook ratePolicyForBook = ratePoliciesForBookRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));
        PolicyStatus deleteStatus = policyStatusRepository.findByName("삭제됨");

        if(ratePolicyForBook.getPolicyStatus().getName().equals("사용됨")){
            ratePolicyForBook.deletePolicy(deleteStatus);
        }
        else{
            ratePolicyForBook.deletePolicy(deleteStatus);
            ratePoliciesForBookRepository.delete(ratePolicyForBook);
        }
        return RatePolicyForBookResponse.changeEntityToDto(ratePolicyForBook);

    }

    // 정률정책 for Category delete
    public RatePolicyForCategoryResponse deleteRatePolicyForCategory(Long id){

        RatePolicyForCategory ratePolicyForCategory = ratePoliciesForCategoryRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));

        PolicyStatus deleteStatus = policyStatusRepository.findByName("삭제됨");

        if(ratePolicyForCategory.getPolicyStatus().getName().equals("사용됨")){
            ratePolicyForCategory.deletePolicy(deleteStatus);
        }
        else{
            ratePolicyForCategory.deletePolicy(deleteStatus);
            ratePoliciesForCategoryRepository.delete(ratePolicyForCategory);
        }
        return RatePolicyForCategoryResponse.changeEntityToDto(ratePolicyForCategory);

    }

    // 정액정책 for Book delete
    public PricePolicyForBookResponse deletePricePolicyForBook(Long id){

        PricePolicyForBook pricePolicyForBook = pricePoliciesForBookRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));

        PolicyStatus deleteStatus = policyStatusRepository.findByName("삭제됨");

        if(pricePolicyForBook.getPolicyStatus().getName().equals("사용됨")){
            pricePolicyForBook.deletePolicy(deleteStatus);
        }
        else{
            pricePolicyForBook.deletePolicy(deleteStatus);
            pricePoliciesForBookRepository.delete(pricePolicyForBook);
        }
        return PricePolicyForBookResponse.changeEntityToDto(pricePolicyForBook);

    }

    // 정액정책 for Category delete
    public PricePolicyForCategoryResponse deletePricePolicyForCategory(Long id){

        PricePolicyForCategory pricePolicyForCategory = pricePoliciesForCategoryRepository.findById(id)
                .orElseThrow(()->new PolicyNotFoundException("해당하는 ID의 정책이 존재하지 않습니다"));

        PolicyStatus deleteStatus = policyStatusRepository.findByName("삭제됨");

        if(pricePolicyForCategory.getPolicyStatus().getName().equals("사용됨")){

            pricePolicyForCategory.deletePolicy(deleteStatus);

        }
        else{

            pricePolicyForCategory.deletePolicy(deleteStatus);
            pricePoliciesForCategoryRepository.delete(pricePolicyForCategory);
        }

        return PricePolicyForCategoryResponse.changeEntityToDto(pricePolicyForCategory);

    }

}
