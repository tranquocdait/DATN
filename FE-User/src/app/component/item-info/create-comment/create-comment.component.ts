import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.scss']
})
export class CreateCommentComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }
  currentRate:number=0;
  @Input() data;
  @Output() output = new EventEmitter();
  editForm: FormGroup;
  ngOnInit() {
  }
  clickClose(){
    this.activeModal.close()
  }
  onSubmit(){
  }
}
