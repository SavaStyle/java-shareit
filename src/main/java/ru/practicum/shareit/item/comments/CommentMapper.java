package ru.practicum.shareIt.item.comments;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentMapper {

    public static Comment fromCommentDto(CommentDto commentDto) {
        return new Comment(commentDto.getText());
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated());
    }
}
