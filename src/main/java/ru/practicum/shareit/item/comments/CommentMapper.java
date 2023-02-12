package ru.practicum.shareIt.item.comments;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentMapper {

    public Comment fromCommentDto(CommentDto commentDto) {
        return new Comment(commentDto.getText());
    }

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated());
    }
}
