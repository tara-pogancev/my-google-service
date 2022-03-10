import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'export-page',
  templateUrl: './export-page.component.html',
  styleUrls: ['./export-page.component.scss'],
})
export class ExportPageComponent implements OnInit {
  exportForm: FormGroup = new FormGroup({});

  constructor() {}

  ngOnInit(): void {
    this.exportForm = new FormGroup({
      type: new FormControl('1', {
        validators: [Validators.required],
      }),
    });
  }

  export() {
    if (this.exportForm.valid) {
    }
  }
}
