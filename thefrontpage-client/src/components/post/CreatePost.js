import { convertToHTML } from 'draft-convert';
import { EditorState } from 'draft-js';
import React, { useState } from 'react';
import { Editor } from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import { Button, Divider, Grid, Input } from 'semantic-ui-react';

const CreatePost = (props) => {
  const { onCreateNewPost } = props;
  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );
  const [body, setBody] = useState('');
  const [title, setTitle] = useState('');
  const [url, setUrl] = useState(null);
  const onEditorStateChange = (event) => {
    setEditorState(event);
    const currentContentAsHTML = convertToHTML(event.getCurrentContent());
    setBody(currentContentAsHTML);
  };
  const onTitleInput = (event, { value }) => {
    setTitle(value);
  };
  const onUrlInput = (event, { value }) => {
    setUrl(value);
  };
  const createPost = () => {
    onCreateNewPost({
      title,
      url,
      body
    });
  };
  return (
    <div>
      <Grid columns={2}>
        <Grid.Column>
          <Input
            placeholder="Enter post title..."
            onChange={onTitleInput}
            fluid
          />
        </Grid.Column>
        <Grid.Column>
          <Input placeholder="Enter image URL..." onChange={onUrlInput} fluid />
        </Grid.Column>
      </Grid>
      <br></br>
      <Editor
        editorState={editorState}
        editorStyle={{
          border: '1px solid #efefef',
          padding: '5px',
          maxHeight: '200px',
          backgroundColor: 'white'
        }}
        placeholder="Enter post body..."
        toolbarClassName="toolbarClassName"
        wrapperClassName="wrapperClassName"
        editorClassName="editorClassName"
        onEditorStateChange={onEditorStateChange}
      />
      <br></br>
      <Divider horizontal>
        <Button color="blue" onClick={createPost}>
          Create post
        </Button>
      </Divider>
    </div>
  );
};

export default CreatePost;
