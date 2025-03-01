package com.cobong.yuja.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cobong.yuja.config.auth.PrincipalDetails;
import com.cobong.yuja.payload.request.comment.CommentRequestDto;
import com.cobong.yuja.service.comment.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
	private final CommentService commentService;
	
	@GetMapping("/api/comment/{boardId}")
	public ResponseEntity<?> GetAllComment(@PathVariable Long boardId) {
		return new ResponseEntity<>(commentService.getCommentsByBoardId(boardId),HttpStatus.OK);
	}
	
	@PostMapping("/api/comment") // done
	public ResponseEntity<?> insertComment(@RequestBody CommentRequestDto dto) {
		dto.setDeleted(false);
		return new ResponseEntity<>(commentService.save(dto),HttpStatus.CREATED);
	}
	
	@PutMapping("/api/comment/{commentId}") // done
	public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
		PrincipalDetails principalDetails = null;
    	Long userId = 0L;
    	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PrincipalDetails) {
    		principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userId = principalDetails.getUserId();
		}
    	dto.setUserId(userId);
		return new ResponseEntity<>(commentService.modify(commentId, dto),HttpStatus.OK);
	}
	
	@DeleteMapping("api/comment/{commentId}") // done
	public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
		PrincipalDetails principalDetails = null;
    	Long userId = 0L;
    	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PrincipalDetails) {
    		principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userId = principalDetails.getUserId();
		}
		//deleted값이 true로 바뀐다.
		return new ResponseEntity<>(commentService.deleteById(commentId, userId),HttpStatus.OK);
	}
}
