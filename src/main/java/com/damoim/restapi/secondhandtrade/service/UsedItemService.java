package com.damoim.restapi.secondhandtrade.service;


import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.dao.UsedItemSearchRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import com.damoim.restapi.secondhandtrade.model.ResponseModifyUsedItemClosed;
import com.damoim.restapi.secondhandtrade.model.ResponseUsedItem;
import com.damoim.restapi.secondhandtrade.model.SearchUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.UsedItemRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UsedItemService {

    private final UsedItemRepository usedItemRepository;
    private final UsedItemSearchRepository usedItemSearchRepository;
    private final DamoimFileUtil damoimFileUtil;
    private final ModelMapper modelMapper;

    public ResponseUsedItem save(UsedItemRequest request, MultipartFile file) {
        UsedItem item = request.toEntity();
        if (file != null) {
            String upload = damoimFileUtil.upload(file);
            item.setTitleImg(upload);
        }
        usedItemRepository.save(item);
        return modelMapper.map(item, ResponseUsedItem.class);
    }

    public UsedItem save(UsedItemRequest request) {
        return usedItemRepository.save(request.toEntity());
    }

    public ResponseUsedItem selectItem(Long no) {
        UsedItem item = getItemFromId(no);
        return modelMapper.map(item, ResponseUsedItem.class);
    }

    public ResponseUsedItem editItem(Long no, UsedItemRequest editRq) {
        UsedItem originItem = getItemFromId(no);
        UsedItem updateItem = usedItemRepository.save(editRq.updateTo(originItem));
        return modelMapper.map(updateItem, ResponseUsedItem.class);
    }

    public ResponseModifyUsedItemClosed itemUpdateToClosed(Long no, String writer) {
        UsedItem item = getItemFromId(no);
        if (!item.isWriter(writer)) {
            throw new AccessDeniedException("작성자 외 수정 불가능");
        }

        ResponseModifyUsedItemClosed closed = item.closed(writer);
        usedItemRepository.save(item);
        return closed;
    }

    public void delete(Long no) {
        UsedItem item = getItemFromId(no);
        usedItemRepository.delete(item);
    }

    private UsedItem getItemFromId(Long no) {
        Optional<UsedItem> item = usedItemRepository.findById(no);
        return item
            .orElseThrow(
                () -> new NotFoundPage(HttpStatus.NOT_FOUND.toString(), String.valueOf(no)));
    }

    public Page<ResponseUsedItem> search(SearchUsedItemRequest request, Pageable pageable) {
        return usedItemSearchRepository.search(request, pageable);
    }
}
